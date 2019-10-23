//
//  ZHComponentProtocol.h
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol ZHComponentProtocol <NSObject>
/** 创建组件 */
- (instancetype)initWithInfo:(NSDictionary *)info dataInfo:(NSDictionary *)dataInfo;
@end

NS_ASSUME_NONNULL_END
