//
//  ZHLayoutManager.m
//  YogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHLayoutManager.h"
#import "ZHRootView.h"
#import "ZHImageComponent.h"
#import "ZHTextComponent.h"
#import "ZHViewComponent.h"
#import "ZHComponentProtocol.h"

@interface ZHLayoutManager ()
/** 注册的组件Map */
@property (nonatomic, strong) NSMutableDictionary *componentMap;
@end

@implementation ZHLayoutManager

//初始化
- (void)install{
    [self registerComponent:@"text" class:[ZHTextComponent class]];
    [self registerComponent:@"image" class:[ZHImageComponent class]];
    [self registerComponent:@"view" class:[ZHViewComponent class]];
}

//注册组件
- (void)registerComponent:(NSString *)name class:(Class)class{
    [self.componentMap setValue:NSStringFromClass(class) forKey:name];
}

- (Class)componentClass:(NSString *)type{
    NSString *str = [self.componentMap valueForKey:type];
    if (!str || str.length == 0) return nil;
    return NSClassFromString(str);
}

- (NSMutableDictionary *)componentMap{
    if (!_componentMap) {
        _componentMap = [@{} mutableCopy];
    }
    return _componentMap;
}

#pragma mark - share

- (instancetype)init{
    if (self = [super init]) {
        // 只加载一次的资源
        static dispatch_once_t onceToken;
        dispatch_once(&onceToken, ^{
            [self install];
        });
    }
    return self;
}

static id _instance;

+ (instancetype)shareManager{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _instance = [[self alloc] init];
    });
    return _instance;
}

+ (instancetype)allocWithZone:(struct _NSZone *)zone{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _instance = [super allocWithZone:zone];
    });
    return _instance;
}

@end
