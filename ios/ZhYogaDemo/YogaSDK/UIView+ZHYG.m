//
//  UIView+ZHYG.m
//  YogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "UIView+ZHYG.h"
#import "ZHYGHeader.h"


@implementation UIView (ZHYG)

//添加边框
- (void)enableBorder{
    ZHYGLayout *layout = self.yoga;
    if (!layout) return;
    CGFloat (^operate) (CGFloat value) = ^ CGFloat (CGFloat value){
        return [NSString stringWithFormat:@"%f", value].floatValue;
    };
    //border width
    CGFloat borderWidth = operate(layout.yg_borderWidth) > 0 ? operate(layout.yg_borderWidth) : 0;
    CGFloat borderLeftWidth = operate(layout.yg_borderLeftWidth);
    borderLeftWidth = (borderLeftWidth > 0 ? borderLeftWidth : borderWidth);
    CGFloat borderTopWidth = operate(layout.yg_borderTopWidth);
    borderTopWidth = (borderTopWidth > 0 ? borderTopWidth : borderWidth);
    CGFloat borderRightWidth = operate(layout.yg_borderRightWidth);
    borderRightWidth = (borderRightWidth > 0 ? borderRightWidth : borderWidth);
    CGFloat borderBottomWidth = operate(layout.yg_borderBottomWidth);
    borderBottomWidth = (borderBottomWidth > 0 ? borderBottomWidth : borderWidth);

    //border color
    UIColor *borderLeftColor = layout.yg_borderLeftColor?:layout.yg_borderColor;
    UIColor *borderRightColor = layout.yg_borderRightColor?:layout.yg_borderColor;
    UIColor *borderBottomColor = layout.yg_borderBottomColor?:layout.yg_borderColor;
    UIColor *borderTopColor = layout.yg_borderTopColor?:layout.yg_borderColor;

    [self borderTop:borderTopColor vaule:borderTopWidth];
    [self borderLeft:borderLeftColor vaule:borderLeftWidth];
    [self borderRight:borderRightColor vaule:borderRightWidth];
    [self borderBottom:borderBottomColor vaule:borderBottomWidth];
    
    //border radius
    CGFloat borderRadius = operate(layout.yg_borderRadius) > 0 ? operate(layout.yg_borderRadius) : 0;
    CGFloat borderTopLeftRadius = operate(layout.yg_borderTopLeftRadius);
    borderTopLeftRadius = (borderTopLeftRadius > 0 ? borderTopLeftRadius : borderRadius);
    CGFloat borderTopRightRadius = operate(layout.yg_borderTopRightRadius);
    borderTopRightRadius = (borderTopRightRadius > 0 ? borderTopRightRadius : borderRadius);
    CGFloat borderBottomLeftRadius = operate(layout.yg_borderBottomLeftRadius);
    borderBottomLeftRadius = (borderBottomLeftRadius > 0 ? borderBottomLeftRadius : borderRadius);
    CGFloat borderBottomRightRadius = operate(layout.yg_borderBottomRightRadius);
    borderBottomRightRadius = (borderBottomRightRadius > 0 ? borderBottomRightRadius : borderRadius);
    
    if (!(borderRadius == 0 && borderTopLeftRadius == 0 &&
         borderTopRightRadius == 0 && borderBottomLeftRadius == 0 && borderBottomRightRadius == 0)) {
        CGFloat width = self.bounds.size.width;
        CGFloat height = self.bounds.size.height;
        
        UIBezierPath *maskPath = [UIBezierPath bezierPath];
        [maskPath moveToPoint:CGPointMake(0, borderTopLeftRadius)];
        if (borderTopLeftRadius > 0) {
            [maskPath addArcWithCenter:CGPointMake(borderTopLeftRadius, borderTopLeftRadius) radius:borderTopLeftRadius startAngle:M_PI endAngle:M_PI + M_PI_2 clockwise:YES];
        }
        [maskPath addLineToPoint:CGPointMake(width - borderTopRightRadius, 0)];
        
        if (borderTopRightRadius > 0) {
            [maskPath addArcWithCenter:CGPointMake(width - borderTopRightRadius, borderTopRightRadius) radius:borderTopRightRadius startAngle:M_PI + M_PI_2 endAngle:0 clockwise:YES];
        }
        [maskPath addLineToPoint:CGPointMake(width, height - borderBottomRightRadius)];
        
        if (borderBottomRightRadius > 0) {
            [maskPath addArcWithCenter:CGPointMake(width - borderBottomRightRadius, height - borderBottomRightRadius) radius:borderBottomRightRadius startAngle:0 endAngle:M_PI_2 clockwise:YES];
        }
        [maskPath addLineToPoint:CGPointMake(borderBottomLeftRadius, height)];
        
        if (borderBottomLeftRadius > 0) {
            [maskPath addArcWithCenter:CGPointMake(borderBottomLeftRadius, height - borderBottomLeftRadius) radius:borderBottomLeftRadius startAngle:M_PI_2 endAngle:M_PI clockwise:YES];
        }
        [maskPath addLineToPoint:CGPointMake(0, borderTopLeftRadius)];
        [maskPath closePath];
        
        CAShapeLayer *maskLayer = [[CAShapeLayer alloc]init];
        maskLayer.frame = self.bounds;
        maskLayer.path = maskPath.CGPath;
        self.layer.mask = maskLayer;
    }
    
    //遍历子视图
    for (UIView *subView in self.subviews) {
        [subView enableBorder];
    }
}
- (void)borderTop:(UIColor *)color vaule:(CGFloat)value{
    value = [NSString stringWithFormat:@"%f", value].floatValue;
    if (value <= 0) return;
    CGRect rect = CGRectMake(0, 0, self.bounds.size.width, value);
    [self enableBorder:color rect:rect];
}
- (void)borderLeft:(UIColor *)color vaule:(CGFloat)value{
    value = [NSString stringWithFormat:@"%f", value].floatValue;
    if (value <= 0) return;
    CGRect rect = CGRectMake(0, 0, value, self.bounds.size.height);
    [self enableBorder:color rect:rect];
}
- (void)borderRight:(UIColor *)color vaule:(CGFloat)value{
    value = [NSString stringWithFormat:@"%f", value].floatValue;
    if (value <= 0) return;
    CGRect rect = CGRectMake(self.bounds.size.width - value, 0, value, self.bounds.size.height);
    [self enableBorder:color rect:rect];
}
- (void)borderBottom:(UIColor *)color vaule:(CGFloat)value{
    value = [NSString stringWithFormat:@"%f", value].floatValue;
    if (value <= 0) return;
    CGRect rect = CGRectMake(0, self.bounds.size.height - value, self.bounds.size.width, value);
    [self enableBorder:color rect:rect];
}
- (void)border:(UIColor *)color vaule:(CGFloat)value{
    [self borderTop:color vaule:value];
    [self borderBottom:color vaule:value];
    [self borderLeft:color vaule:value];
    [self borderRight:color vaule:value];
}

- (void)enableBorder:(UIColor *)color rect:(CGRect)rect{
    if (!color) return;
    UIBezierPath *bezierPath = [UIBezierPath bezierPathWithRect:rect];
    CAShapeLayer *shapeLayer = [CAShapeLayer layer];
    shapeLayer.path = bezierPath.CGPath;
    shapeLayer.fillColor = color.CGColor;
    [self.layer addSublayer:shapeLayer];
    //    CALayer *layer = [CALayer layer];
    //    layer.frame = rect;
    //    layer.backgroundColor = color.CGColor;
    //    [self.layer addSublayer:layer];
}

@end
