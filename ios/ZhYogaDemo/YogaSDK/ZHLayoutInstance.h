//
//  ZHLayoutInstance.h
//  YogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "ZHRootView.h"
#import "ZHComponent.h"

NS_ASSUME_NONNULL_BEGIN

@interface ZHLayoutInstance : NSObject

- (void)renderByInfo:(NSDictionary *)info dataInfo:(NSDictionary *)dataInfo;
@property (nonatomic, assign) CGRect frame;
@property (nonatomic, strong, readonly) ZHRootView *rootView;
/** 点击事件 */
@property (nonatomic,copy) void (^click) (ZHComponent *component, NSDictionary *action);

/** 回调 */
/** view创建 **/
@property (nonatomic, copy) void (^onCreate)(UIView *wrapView);
/** 布局完成 **/
@property (nonatomic, copy) void (^onDidLayout)(UIView *contentView);

@end

NS_ASSUME_NONNULL_END
