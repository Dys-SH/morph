//
//  ZHComponent.m
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHComponent.h"

@interface ZHComponent () <UIGestureRecognizerDelegate>
/** 组件id */
@property (nonatomic, strong) NSString *Id;
/** 生成的view */
@property (nonatomic, strong) UIView *view;
/** 布局信息 */
@property (nonatomic, strong) NSDictionary *info;
/** 数据信息 */
@property (nonatomic, strong) NSDictionary *dataInfo;
/** action */
@property (nonatomic, strong) NSDictionary *action;
@property (nonatomic, strong) NSDictionary *activeAction;
@end

@implementation ZHComponent

- (instancetype)initWithInfo:(NSDictionary *)info dataInfo:(NSDictionary *)dataInfo{
    if (!info || info.allKeys.count == 0) return nil;
    self = [super init];
    if (self) {
        self.Id = ZHJsonStringValue(info, @"id")?:@"--";
        self.info = info;
        self.dataInfo = dataInfo;
        //创建视图
        self.view = [self createView];
        //配置通用属性
        [self configUntil];
        //配置事件
        [self configAction];
    }
    return self;
}
/** 创建组件视图 */
- (UIView *)createView{
    return nil;
}

/** 配置通用属性 */
- (void)configUntil{
    NSDictionary *info = self.info;
    //颜色
    UIColor *bgColor = ZHColor(ZHOperationData(ZHJsonValue(info, @"background-color"), self.dataInfo), [UIColor clearColor]);
    self.view.backgroundColor = bgColor;
    
    //背景图片
    NSString *bgImageStr = ZHJsonStringValue(info, @"background-image");
    if (bgImageStr.length > 0) {
        UIImageView *imageView = [[UIImageView alloc] init];
        [imageView sd_setImageWithURL:[NSURL URLWithString:bgImageStr] placeholderImage:nil];
        [imageView configureLayoutWithBlock:^(ZHYGLayout * _Nonnull layout) {
            layout.isEnabled = true;
            layout.position = YGPositionTypeAbsolute;
            
            NSString *paddingTop = ZHJsonStringValue(info, @"padding-top");
            layout.top = paddingTop ? ZHNumberMap(paddingTop) : YGValueZero;
            NSString *paddingBottom = ZHJsonStringValue(info, @"padding-bottom");
            layout.bottom = paddingBottom ? ZHNumberMap(paddingBottom) : YGValueZero;
            NSString *paddingLeft = ZHJsonStringValue(info, @"padding-left");
            layout.left = paddingLeft ? ZHNumberMap(paddingLeft) : YGValueZero;
            NSString *paddingRight = ZHJsonStringValue(info, @"padding-right");
            layout.right = paddingRight ? ZHNumberMap(paddingRight) : YGValueZero;
        }];
        [self.view addSubview:imageView];
    }
}

- (void)configAction{
    self.action = ZHJsonValue(self.info, @"action");
    self.activeAction = ZHJsonValue(self.info, @"action-active");
    if (!self.action || self.action.allKeys.count == 0) {
        if ([self.view isKindOfClass:[UILabel class]] ||
            [self.view isKindOfClass:[UIImageView class]]) {
            self.view.userInteractionEnabled = NO;
        }
        return;
    }
    UIGestureRecognizer *gesture;
//    if (self.activeAction) {
        UILongPressGestureRecognizer *pressGesture = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(pressGesture:)];
        pressGesture.minimumPressDuration = 0.05;
        gesture = pressGesture;
//    }else{
//        gesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(pressGesture:)];
//    }
    gesture.delegate = self;
    [self.view addGestureRecognizer:gesture];
    self.view.userInteractionEnabled = YES;
}

//- (BOOL)gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer{
//    NSLog(@"----------gestureRecognizerShouldBegin----------");
//    return self.shouldResponseGesture;
//}

//允许手势透传
- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer{
    if ([otherGestureRecognizer isKindOfClass:NSClassFromString(@"UIScrollViewPanGestureRecognizer")]) {
        //有滑动手势
        return YES;
    }
    return NO;
}

- (void)pressGesture:(UIGestureRecognizer *)gesture{
    
    UIView *gestureView = gesture.view;
    NSLog(@"-gestureView---%@----",gestureView);
    CGPoint point = [gesture locationInView:gestureView];
    NSLog(@"-point---%@----",NSStringFromCGPoint(point));
    if (CGRectContainsPoint(gestureView.bounds, point)) {
        NSLog(@"--------pressGesture111------------");
    }else{
        NSLog(@"--------pressGesture222------------");
    }
    
    NSDictionary *activeAction = self.activeAction;
    NSDictionary *action = self.action;
    
    UIColor *activeBgColor = ZHColor(ZHOperationData(ZHJsonValue(activeAction, @"background-color"), self.dataInfo), nil);
    UIColor *bgColor = ZHColor(ZHOperationData(ZHJsonValue(self.info, @"background-color"), self.dataInfo), [UIColor clearColor]);
    
    __weak __typeof__(self) __self = self;
    __weak __typeof__(gesture) __gesture = gesture;
    //高亮
    void (^highlight)(void) = ^{
        if (activeBgColor) __self.view.backgroundColor = activeBgColor;
    };
    //恢复
    void (^revert)(void) = ^{
        if (activeBgColor) __self.view.backgroundColor = bgColor;
        __gesture.enabled = YES;
    };

    switch (gesture.state){
        case UIGestureRecognizerStateBegan:{
            NSLog(@"---------UIGestureRecognizerStateBegan-----------");
            highlight();
        }
            break;
        case UIGestureRecognizerStateChanged:{
            NSLog(@"---------UIGestureRecognizerStateChanged-----------");
            gesture.enabled = NO;
        }
            break;
        case UIGestureRecognizerStateEnded:{
            NSLog(@"---------UIGestureRecognizerStateEnded-----------");
            revert();
            id data = ZHOperationData(ZHJsonValue(action, @"data"), self.dataInfo);
            if (self.click) self.click(@{@"id": self.Id, @"data": data?:@{}});
        }
            break;
        case UIGestureRecognizerStateCancelled:{
            NSLog(@"---------UIGestureRecognizerStateCancelled-----------");
            revert();
        }
            break;
        case UIGestureRecognizerStateFailed:{
            NSLog(@"---------UIGestureRecognizerStateFailed-----------");
            revert();
        }
            break;
        default:{
            NSLog(@"---------UIGestureRecognizerStateOther-----------");
            revert();
        }
            break;
    }
    NSLog(@"\n");
}

@end
