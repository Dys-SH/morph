//
//  ZHView.m
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHView.h"

@implementation ZHView

//- (UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event{
//    if (self.hidden || !self.userInteractionEnabled) {
//        return nil;
//    }
//
//    UIView* result;
//    for (UIView* subView in [self.subviews reverseObjectEnumerator]) {
//        CGPoint subPoint = [self convertPoint:point toView:subView];
//
//        result = [subView hitTest:subPoint withEvent:event];
//
//        BOOL contain = CGRectContainsPoint(subView.bounds, subPoint);
//        if (contain) {
//            if (result) {
//                return result;
//            }else{
//                continue;
//            }
//        }else{
//            continue;
//        }
//    }
//
//
//    result = [super hitTest:point withEvent:event];
//    if (result) {
//        return result;
//    }
//
//    // if clips to bounds, no need to detect outside views.
//    if (self.clipsToBounds) {
//        return nil;
//    }
//
//    return nil;
//}

@end
