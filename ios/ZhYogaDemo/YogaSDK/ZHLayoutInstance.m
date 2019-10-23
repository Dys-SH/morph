//
//  ZHLayoutInstance.m
//  YogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHLayoutInstance.h"
#import "ZHLayoutManager.h"

@interface ZHLayoutInstance ()
@property (nonatomic, strong) ZHRootView *rootView;
/** 创建的组件Map */
@property (nonatomic, retain) NSMutableDictionary *componentRunMap;
/** 布局信息 */
@property (nonatomic, strong) NSDictionary *info;
/** 数据信息 */
@property (nonatomic, strong) NSDictionary *dataInfo;
@end

@implementation ZHLayoutInstance

#pragma mark - life cycle

- (void)dealloc{
    if (!self.rootView) return;
    [self.rootView removeFromSuperview];
}

#pragma mark - render

- (void)renderByInfo:(NSDictionary *)info dataInfo:(NSDictionary *)dataInfo{
//    if (CGRectEqualToRect(CGRectZero, self.frame)) return nil;
    
    self.info = [info copy];
    self.dataInfo = [dataInfo copy];
    
    //1、创建rootView
    ZHRootView *rootView = [[ZHRootView alloc] init];
    self.rootView = rootView;
    rootView.frame = self.frame;
    [rootView configureLayoutWithBlock:^(ZHYGLayout * layout) {
        layout.isEnabled = YES;
    }];
    if (self.onCreate) {
        self.onCreate(rootView);
    }
    
    //2、布局
    UIView *view = [self layoutView:info];
    if (view) {
        [rootView addSubview:view];
        
        //3、应用布局
        [rootView.yoga applyLayoutPreservingOrigin:YES];
        
        //4、处理border样式
        [rootView enableBorder];
    }
    
    if (self.onDidLayout) {
        self.onDidLayout(view);
    }
}

//布局子控件
- (UIView *)layoutView:(NSDictionary *)json{
    NSString *type = ZHJsonStringValue(json, @"type");
    if (!type || type.length == 0) return nil;

    //创建组件
    Class comClass = [[ZHLayoutManager shareManager] componentClass:type];
    ZHComponent *component = [[comClass alloc] initWithInfo:json dataInfo:self.dataInfo];
    UIView *view = component.view;
    if (!view) return nil;
    
    //设置事件
    __weak __typeof__(self) __self = self;
    __weak __typeof__(component) __component = component;
    component.click = ^(NSDictionary * _Nonnull action) {
        if (__self.click) __self.click(__component, action);
    };
    
    //保存组件
    [self.componentRunMap setObject:component forKey:[NSString stringWithFormat:@"%p", component]];
    
    //json取值
    void (^oprate) (NSDictionary *, NSString *, void (^) (NSString *value)) = ^(NSDictionary *json, NSString *key, void (^block) (NSString *value)){
        if (![json.allKeys containsObject:key]) return;
        NSString *value = ZHJsonStringValue(json, key);
        if (!value) return;
        block(value);
    };
    
    //布局属性
    [view configureLayoutWithBlock:^(ZHYGLayout * layout) {
        layout.isEnabled = true;
        
        //        layout.direction = YGDirectionInherit;
        //设置默认横向布局
        layout.flexDirection = YGFlexDirectionRow;
        oprate(json, @"flex-direction", ^(NSString *value){
            layout.flexDirection = ZHFlexDirectionMap(value);
        });
        oprate(json, @"justify-content", ^(NSString *value){
            layout.justifyContent = ZHJustifyContentMap(value);
        });
        oprate(json, @"align-content", ^(NSString *value){
            layout.alignContent = ZHAlignMap(value);
        });
        oprate(json, @"align-items", ^(NSString *value){
            layout.alignItems = ZHAlignMap(value);
        });
        oprate(json, @"align-self", ^(NSString *value){
            layout.alignSelf = ZHAlignMap(value);
        });
        oprate(json, @"position", ^(NSString *value){
            layout.position = ZHPositionMap(value);
        });
        oprate(json, @"flex-wrap", ^(NSString *value){
            layout.flexWrap = ZHFlexWrapMap(value);
        });
        oprate(json, @"overflow", ^(NSString *value){
            layout.overflow = ZHOverflowMap(value);
        });
        if ([json.allKeys containsObject:@"display"]) {
            NSString *disPlayResult = ZHOperationData(ZHJsonValue(json, @"display"), self.dataInfo);
            NSString *value = disPlayResult ? [NSString stringWithFormat:@"%@",disPlayResult] : @"";
            layout.display = ZHDisplayMap(value);
        }
        
        //flex
        oprate(json, @"flex", ^(NSString *value){
            layout.flex = value.floatValue;
        });
        oprate(json, @"flex-grow", ^(NSString *value){
            layout.flexGrow = value.floatValue;
        });
        oprate(json, @"flex-shrink", ^(NSString *value){
            layout.flexShrink = value.floatValue;
        });
        oprate(json, @"flex-basis", ^(NSString *value){
            layout.flexBasis = ZHNumberMap(value);
        });
        
        //left top right bottom start end
        oprate(json, @"left", ^(NSString *value){
            layout.left = ZHNumberMap(value);
        });
        oprate(json, @"top", ^(NSString *value){
            layout.top = ZHNumberMap(value);
        });
        oprate(json, @"right", ^(NSString *value){
            layout.right = ZHNumberMap(value);
        });
        oprate(json, @"bottom", ^(NSString *value){
            layout.bottom = ZHNumberMap(value);
        });
        oprate(json, @"start", ^(NSString *value){
            layout.start = ZHNumberMap(value);
        });
        oprate(json, @"end", ^(NSString *value){
            layout.end = ZHNumberMap(value);
        });
        
        //margin
        oprate(json, @"margin-start", ^(NSString *value){
            layout.marginStart = ZHNumberMap(value);
        });
        oprate(json, @"margin-end", ^(NSString *value){
            layout.marginEnd = ZHNumberMap(value);
        });
        oprate(json, @"margin-horizontal", ^(NSString *value){
            layout.marginHorizontal = ZHNumberMap(value);
        });
        oprate(json, @"margin-vertical", ^(NSString *value){
            layout.marginVertical = ZHNumberMap(value);
        });
        oprate(json, @"margin", ^(NSString *value){
            NSArray *marginArr = [value componentsSeparatedByString:@" "];
            NSUInteger count = marginArr.count;
            if (count == 1) {
                layout.margin = ZHNumberMap(marginArr[0]);
            }else{
                layout.marginTop = ZHNumberMap(marginArr[0]);
                layout.marginRight = ZHNumberMap(marginArr[1]);
                layout.marginBottom = ZHNumberMap(count == 2 ? marginArr[0] : marginArr[2]);
                layout.marginLeft = ZHNumberMap(count == 2 || count == 3 ? marginArr[1] : marginArr[3]);
            }
        });
        oprate(json, @"margin-left", ^(NSString *value){
            layout.marginLeft = ZHNumberMap(value);
        });
        oprate(json, @"margin-top", ^(NSString *value){
            layout.marginTop = ZHNumberMap(value);
        });
        oprate(json, @"margin-right", ^(NSString *value){
            layout.marginRight = ZHNumberMap(value);
        });
        oprate(json, @"margin-bottom", ^(NSString *value){
            layout.marginBottom = ZHNumberMap(value);
        });
        
        //padding
        oprate(json, @"padding-start", ^(NSString *value){
            layout.paddingStart = ZHNumberMap(value);
        });
        oprate(json, @"padding-end", ^(NSString *value){
            layout.paddingEnd = ZHNumberMap(value);
        });
        oprate(json, @"padding-horizontal", ^(NSString *value){
            layout.paddingHorizontal = ZHNumberMap(value);
        });
        oprate(json, @"padding-vertical", ^(NSString *value){
            layout.paddingVertical = ZHNumberMap(value);
        });
        oprate(json, @"padding", ^(NSString *value){
            NSArray *paddingArr = [value componentsSeparatedByString:@" "];
            NSUInteger count = paddingArr.count;
            if (count == 1) {
                layout.padding = ZHNumberMap(paddingArr[0]);
            }else{
                layout.paddingTop = ZHNumberMap(paddingArr[0]);
                layout.paddingRight = ZHNumberMap(paddingArr[1]);
                layout.paddingBottom = ZHNumberMap(count == 2 ? paddingArr[0] : paddingArr[2]);
                layout.paddingLeft = ZHNumberMap(count == 2 || count == 3 ? paddingArr[1] : paddingArr[3]);
            }
        });
        oprate(json, @"padding-left", ^(NSString *value){
            layout.paddingLeft = ZHNumberMap(value);
        });
        oprate(json, @"padding-top", ^(NSString *value){
            layout.paddingTop = ZHNumberMap(value);
        });
        oprate(json, @"padding-right", ^(NSString *value){
            layout.paddingRight = ZHNumberMap(value);
        });
        oprate(json, @"padding-bottom", ^(NSString *value){
            layout.paddingBottom = ZHNumberMap(value);
        });
        
        //border-width
        oprate(json, @"border-left-width", ^(NSString *value){
            layout.yg_borderLeftWidth = value.floatValue;
        });
        oprate(json, @"border-top-width", ^(NSString *value){
            layout.yg_borderTopWidth = value.floatValue;
        });
        oprate(json, @"border-right-width", ^(NSString *value){
            layout.yg_borderRightWidth = value.floatValue;
        });
        oprate(json, @"border-bottom-width", ^(NSString *value){
            layout.yg_borderBottomWidth = value.floatValue;
        });
        oprate(json, @"border-start-width", ^(NSString *value){
            layout.yg_borderStartWidth = value.floatValue;
        });
        oprate(json, @"border-end-width", ^(NSString *value){
            layout.yg_borderEndWidth = value.floatValue;
        });
        oprate(json, @"border-width", ^(NSString *value){
            layout.yg_borderWidth = value.floatValue;
        });
        
        //border color
        oprate(json, @"border-left-color", ^(NSString *value){
            layout.yg_borderLeftColor = ZHColor(value, nil);
        });
        oprate(json, @"border-top-color", ^(NSString *value){
            layout.yg_borderTopColor = ZHColor(value, nil);
        });
        oprate(json, @"border-right-color", ^(NSString *value){
            layout.yg_borderRightColor = ZHColor(value, nil);
        });
        oprate(json, @"border-bottom-color", ^(NSString *value){
            layout.yg_borderBottomColor = ZHColor(value, nil);
        });
        oprate(json, @"border-color", ^(NSString *value){
            layout.yg_borderColor = ZHColor(value, nil);
        });
        
        //border radius
        oprate(json, @"border-top-left-radius", ^(NSString *value){
            layout.yg_borderTopLeftRadius = value.floatValue;
        });
        oprate(json, @"border-top-right-radius", ^(NSString *value){
            layout.yg_borderTopRightRadius = value.floatValue;
        });
        oprate(json, @"border-bottom-left-radius", ^(NSString *value){
            layout.yg_borderBottomLeftRadius = value.floatValue;
        });
        oprate(json, @"border-bottom-right-radius", ^(NSString *value){
            layout.yg_borderBottomRightRadius = value.floatValue;
        });
        oprate(json, @"border-radius", ^(NSString *value){
            layout.yg_borderRadius = value.floatValue;
        });
        
        //width height
        oprate(json, @"width", ^(NSString *value){
            layout.width = ZHNumberMap(value);
        });
        oprate(json, @"height", ^(NSString *value){
            layout.height = ZHNumberMap(value);
        });
        oprate(json, @"min-width", ^(NSString *value){
            layout.minWidth = ZHNumberMap(value);
        });
        oprate(json, @"min-height", ^(NSString *value){
            layout.minHeight = ZHNumberMap(value);
        });
        oprate(json, @"max-width", ^(NSString *value){
            layout.maxWidth = ZHNumberMap(value);
        });
        oprate(json, @"min-height", ^(NSString *value){
            layout.maxHeight = ZHNumberMap(value);
        });
        
        //yoga特有属性 不兼容flexbox
        oprate(json, @"aspect-ratio", ^(NSString *value){
            layout.aspectRatio = value.floatValue;
        });
    }];
    
    //布局子控件
    NSArray *children = ZHJsonValue(json, @"children");
    for (NSDictionary *subJson in children) {
        UIView *subView = [self layoutView:subJson];
        if (subView) [view addSubview:subView];
    }
    return view;
}

#pragma mark - setter

- (void)setFrame:(CGRect)frame{
    if (CGRectEqualToRect(frame, self.frame)) return;
    _frame = frame;
    if (!self.rootView) return;
    
    self.rootView.frame = frame;
    
//    UIView *superView = self.rootView.superview;
//    UIView *originView = self.rootView;
//
//    UIView *view = [self renderByInfo:self.info dataInfo:self.dataInfo];
//    if (!view) return;
//    [originView removeFromSuperview];
//    [superView addSubview:view];
}

#pragma mark - getter

- (NSMutableDictionary *)componentRunMap{
    if (!_componentRunMap) {
        _componentRunMap = [@{} mutableCopy];
    }
    return _componentRunMap;
}

@end
