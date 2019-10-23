//
//  ZHImageComponent.m
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHImageComponent.h"

@implementation ZHImageComponent

- (UIView *)createView{
    UIImageView *imageView = [[UIImageView alloc] init];
    
    NSString *modeResult = ZHOperationData(ZHJsonValue(self.info, @"mode"), self.dataInfo);
    imageView.contentMode = ZHContentMode(modeResult);
    
    NSString *srcResult = ZHOperationData(ZHJsonValue(self.info, @"src"), self.dataInfo);
    NSString *src = srcResult;
    if (src.length > 0) [imageView sd_setImageWithURL:[NSURL URLWithString:src] placeholderImage:nil];
    
    imageView.clipsToBounds = YES;
    return imageView;
}

//- (void)tapGesture:(UITapGestureRecognizer *)tap{
//    NSLog(@"-----------ZHComponent---------");
//}

@end
