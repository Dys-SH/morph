//
//  ZHDemoController.m
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHDemoController.h"
#import "ZHLayoutInstance.h"

@interface ZHDemoController ()
@property (nonatomic, strong) NSDictionary *info;
@property (nonatomic, strong) NSDictionary *dataInfo;
@property (nonatomic,strong) ZHLayoutInstance *instance;

@end

@implementation ZHDemoController

- (instancetype)initWithInfo:(NSDictionary *)info dataInfo:(NSDictionary *)dataInfo{
    self = [super init];
    if (self) {
        self.info = info;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.navigationController.navigationBar setTranslucent:NO];
    self.view.backgroundColor = [UIColor whiteColor];
    
    //参数渲染
    [self renderByInfo:self.info dataInfo:self.dataInfo];
}

- (void)renderByInfo:(NSDictionary *)info dataInfo:(NSDictionary *)dataInfo{
//    NSLog(@"--------布局start------------");
//    NSLog(@"%@",ZHDateString());
    self.instance = [[ZHLayoutInstance alloc] init];
    self.instance.frame = self.view.bounds;
    __weak __typeof__(self) __self = self;
    self.instance.click = ^(ZHComponent *component, NSDictionary *action) {
        NSLog(@"%@",component);
        NSLog(@"%@",action);
    };
    self.instance.onCreate = ^(UIView * _Nonnull wrapView) {
        if (wrapView) [__self.view addSubview:wrapView];
    };
    self.instance.onDidLayout = ^(UIView * _Nonnull contentView) {
    };
    [self.instance renderByInfo:info dataInfo:dataInfo];
//    NSLog(@"---------布局finish-----------");
//    NSLog(@"%@",ZHDateString());
}

@end
