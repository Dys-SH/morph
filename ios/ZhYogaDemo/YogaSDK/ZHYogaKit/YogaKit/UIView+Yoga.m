/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import "UIView+Yoga.h"
#import "YGLayout+Private.h"
#import <objc/runtime.h>

static const void *kYGYogaAssociatedKey = &kYGYogaAssociatedKey;

@implementation UIView (YogaKit)

- (ZHYGLayout *)yoga
{
  ZHYGLayout *yoga = objc_getAssociatedObject(self, kYGYogaAssociatedKey);
  if (!yoga) {
    yoga = [[ZHYGLayout alloc] initWithView:self];
    objc_setAssociatedObject(self, kYGYogaAssociatedKey, yoga, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
  }

  return yoga;
}

- (BOOL)isYogaEnabled
{
  return objc_getAssociatedObject(self, kYGYogaAssociatedKey) != nil;
}

- (void)configureLayoutWithBlock:(ZHLayoutConfigurationBlock)block
{
  if (block != nil) {
    block(self.yoga);
  }
}

@end
