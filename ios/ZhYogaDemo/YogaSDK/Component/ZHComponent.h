//
//  ZHComponent.h
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "ZHYGHeader.h"

#import <SDWebImage/UIImageView+WebCache.h>

NS_ASSUME_NONNULL_BEGIN

@interface ZHComponent : NSObject <ZHComponentProtocol>
/** 组件id */
@property (nonatomic, strong, readonly) NSString *Id;
/** 生成的view */
@property (nonatomic, strong, readonly) UIView *view;
/** 布局信息 */
@property (nonatomic, strong, readonly) NSDictionary *info;
/** 数据信息 */
@property (nonatomic, strong, readonly) NSDictionary *dataInfo;
/** 点击事件 */
@property (nonatomic,copy) void (^click) (NSDictionary *action);

@end

NS_ASSUME_NONNULL_END
