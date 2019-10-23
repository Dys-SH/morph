//
//  ZHYGLayout.h
//  YogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "YGLayout.h"

NS_ASSUME_NONNULL_BEGIN

@interface ZHYGLayout : YGLayout

//border width
@property (nonatomic, readwrite, assign) CGFloat yg_borderLeftWidth;
@property (nonatomic, readwrite, assign) CGFloat yg_borderTopWidth;
@property (nonatomic, readwrite, assign) CGFloat yg_borderRightWidth;
@property (nonatomic, readwrite, assign) CGFloat yg_borderBottomWidth;
@property (nonatomic, readwrite, assign) CGFloat yg_borderStartWidth;
@property (nonatomic, readwrite, assign) CGFloat yg_borderEndWidth;
@property (nonatomic, readwrite, assign) CGFloat yg_borderWidth;

//border color
@property (nonatomic, readwrite, strong) UIColor *yg_borderLeftColor;
@property (nonatomic, readwrite, strong) UIColor *yg_borderTopColor;
@property (nonatomic, readwrite, strong) UIColor *yg_borderRightColor;
@property (nonatomic, readwrite, strong) UIColor *yg_borderBottomColor;
@property (nonatomic, readwrite, strong) UIColor *yg_borderColor;

//border radius
@property (nonatomic, readwrite, assign) CGFloat yg_borderTopLeftRadius;
@property (nonatomic, readwrite, assign) CGFloat yg_borderTopRightRadius;
@property (nonatomic, readwrite, assign) CGFloat yg_borderBottomLeftRadius;
@property (nonatomic, readwrite, assign) CGFloat yg_borderBottomRightRadius;
@property (nonatomic, readwrite, assign) CGFloat yg_borderRadius;

@end

NS_ASSUME_NONNULL_END
