//
//  ZHLayoutManager.h
//  YogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface ZHLayoutManager : NSObject
/** 注册的组件Map */
- (Class)componentClass:(NSString *)type;
+ (instancetype)shareManager;

@end

NS_ASSUME_NONNULL_END
