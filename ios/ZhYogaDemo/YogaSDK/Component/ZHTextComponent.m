//
//  ZHTextComponent.m
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHTextComponent.h"

@implementation ZHTextComponent

- (UIView *)createView{
    NSDictionary *info = self.info;
    //字体
    UIFont *font;
    
    NSString *fontSizeResult = ZHOperationData(ZHJsonValue(info, @"font-size"), self.dataInfo);
    CGFloat fontSize = fontSizeResult ? fontSizeResult.floatValue : 16;
    
    NSString *fontWeightResult = ZHOperationData(ZHJsonValue(info, @"font-weight"), self.dataInfo);
    NSString *fontWeight = fontWeightResult?:@"normal";
    if ([fontWeight isEqualToString:@"bold"]) {
        font = [UIFont boldSystemFontOfSize:fontSize];
    }else{
        font = [UIFont systemFontOfSize:fontSize];
    }
    //颜色"#0588FF"
    UIColor *textColor = ZHColor(ZHOperationData(ZHJsonValue(info, @"color"), self.dataInfo), [UIColor blackColor]);
    
    //文字
    NSString *textResult = ZHOperationData(ZHJsonValue(info, @"text"), self.dataInfo);
    NSString *text = textResult ? [NSString stringWithFormat:@"%@",textResult] : @"";
    
    //行数
    NSString *linesResult = ZHOperationData(ZHJsonValue(info, @"lines"), self.dataInfo);
    NSInteger lines = linesResult ? linesResult.integerValue : 1;
    
    //align
    NSString *alignResult = ZHOperationData(ZHJsonValue(info, @"text-align"), self.dataInfo);
    NSTextAlignment align = ZHTextAlign(alignResult ? [NSString stringWithFormat:@"%@",alignResult] : nil);
    
    //样式
    NSMutableParagraphStyle *style = [NSMutableParagraphStyle new];
    style.alignment = align;
    
    //行间距
    if ([info.allKeys containsObject:@"line-spacing"]) {
        NSString *lineSpacingResult = ZHOperationData(ZHJsonValue(info, @"line-spacing"), self.dataInfo);
        CGFloat lineSpacing = lineSpacingResult.floatValue;
        style.lineSpacing = lineSpacing;
    }
    
    //行高
    if ([info.allKeys containsObject:@"line-height"]) {
        NSString *lineHeightResult = ZHOperationData(ZHJsonValue(info, @"line-height"), self.dataInfo);
        CGFloat lineHeight = lineHeightResult.floatValue;
        style.minimumLineHeight = lineHeight;
    }
    
    NSMutableAttributedString *attStr = [[NSMutableAttributedString alloc] initWithString:text];
    [attStr addAttributes:@{NSFontAttributeName: font,
                            NSForegroundColorAttributeName: textColor,
                            NSParagraphStyleAttributeName: style}
                    range:NSMakeRange(0, attStr.length)];
    
    UILabel *label = [[UILabel alloc] init];
    label.attributedText = attStr;
    label.numberOfLines = lines;
    label.lineBreakMode = NSLineBreakByTruncatingTail;
    return label;
}

//textAlign
static NSTextAlignment ZHTextAlign(NSString *value) {
    NSArray *arr = @[@"left", @"center", @"right"];
    switch ([arr indexOfObject:value]) {
        case 1:
            return NSTextAlignmentCenter;
        case 2:
            return NSTextAlignmentRight;
        default:
            return NSTextAlignmentLeft;
    }
}

//- (void)tapGesture:(UITapGestureRecognizer *)tap{
//    NSLog(@"-----------ZHTextComponent---------");
//}


@end
