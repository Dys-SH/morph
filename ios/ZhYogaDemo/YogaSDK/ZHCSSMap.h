//
//  ZHCSSMap.h
//  YogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "UIView+Yoga.h"
#import "YGLayout.h"

//YGEnums.cpp文件有完整map    YGFlexDirectionToString方法
#define ZHCSSFunc __attribute__((unused)) static

//json
ZHCSSFunc id ZHJsonValue(NSDictionary *json, NSString *key) {
    id value = [json valueForKey:key];
    if (!value || value == [NSNull null]) return nil;
    return value;
}
ZHCSSFunc NSString * ZHJsonStringValue(NSDictionary *json, NSString *key) {
    id value = [json valueForKey:key];
    if (!value || value == [NSNull null]) return nil;
    if ([value isKindOfClass:[NSString class]]) {
        if ([(NSString *)value length] == 0) return nil;
        return value;
    }
    @try {
        return [NSString stringWithFormat:@"%@",value];
    }
    @catch (NSException *exception) {
        return @"";
    }
}

//map映射
//FlexDirection
ZHCSSFunc YGFlexDirection ZHFlexDirectionMap(NSString *value) {
    NSArray *arr = @[@"column", @"column-reverse", @"row", @"row-reverse"];
    switch ([arr indexOfObject:value]) {
        case 1:
            return YGFlexDirectionColumnReverse;
        case 2:
            return YGFlexDirectionRow;
        case 3:
            return YGFlexDirectionRowReverse;
        default:
            return YGFlexDirectionColumn;
    }
}
//justifyContent
ZHCSSFunc YGJustify ZHJustifyContentMap(NSString *value) {
    NSArray *arr = @[@"flex-start", @"center", @"flex-end", @"space-between", @"space-around", @"space-evenly"];
    switch ([arr indexOfObject:value]) {
        case 1:
            return YGJustifyCenter;
        case 2:
            return YGJustifyFlexEnd;
        case 3:
            return YGJustifySpaceBetween;
        case 4:
            return YGJustifySpaceAround;
        case 5:
            return YGJustifySpaceEvenly;
        default:
            return YGJustifyFlexStart;
    }
}
//align：align-items、align-content、align-self
ZHCSSFunc YGAlign ZHAlignMap(NSString *value) {
    NSArray *arr = @[@"auto", @"flex-start", @"center", @"flex-end", @"stretch", @"baseline", @"space-between", @"space-around"];
    switch ([arr indexOfObject:value]) {
        case 0:
            return YGAlignAuto;
        case 1:
            return YGAlignFlexStart;
        case 2:
            return YGAlignCenter;
        case 3:
            return YGAlignFlexEnd;
        case 4:
            return YGAlignStretch;
        case 5:
            return YGAlignBaseline;
        case 6:
            return YGAlignSpaceBetween;
        case 7:
            return YGAlignSpaceAround;
        default:
            return YGAlignFlexStart;
    }
}

//position
ZHCSSFunc YGPositionType ZHPositionMap(NSString *value) {
    NSArray *arr = @[@"relative", @"absolute"];
    switch ([arr indexOfObject:value]) {
        case 1:
            return YGPositionTypeAbsolute;
        default:
            return YGPositionTypeRelative;
    }
}

//flex-wrap
ZHCSSFunc YGWrap ZHFlexWrapMap(NSString *value) {
    NSArray *arr = @[@"no-wrap", @"wrap", @"wrap-reverse"];
    switch ([arr indexOfObject:value]) {
        case 1:
            return YGWrapWrap;
        case 2:
            return YGWrapWrapReverse;
        default:
            return YGWrapNoWrap;
    }
}

//overflow
ZHCSSFunc YGOverflow ZHOverflowMap(NSString *value) {
    NSArray *arr = @[@"visible", @"hidden", @"scroll"];
    switch ([arr indexOfObject:value]) {
        case 1:
            return YGOverflowHidden;
        case 2:
            return YGOverflowScroll;
        default:
            return YGOverflowVisible;
    }
}

//display
ZHCSSFunc YGDisplay ZHDisplayMap(NSString *value) {
    NSArray *arr = @[@"flex", @"none"];
    switch ([arr indexOfObject:value]) {
        case 1:
            return YGDisplayNone;
        default:
            return YGDisplayFlex;
    }
}

/**  YGValue
 left top right bottom start end
 margin
 padding
 */
ZHCSSFunc YGValue ZHNumberMap(NSString *value) {
    if ([value hasSuffix:@"%"]) {
        value = [value substringToIndex:value.length - 1];
        return YGPercentValue(value.floatValue);
    }
    if ([value isEqualToString:@"fill"]) {
        return YGPercentValue(100);
    }
    if ([value isEqualToString:@"auto"]) {
        return YGValueAuto;
    }
    if ([value hasSuffix:@"px"]) {
        value = [value substringToIndex:value.length - 2];
    }
    return YGPointValue(value.floatValue);
}

//color
ZHCSSFunc UIColor * ZHColor(NSString *value, UIColor *defaultColor) {
    NSString *colorString = [[value stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] lowercaseString];
    if ([colorString hasPrefix:@"0x"] || [colorString hasPrefix:@"#"]) {
        unsigned rgbValue = 0;
        colorString = [colorString stringByReplacingOccurrencesOfString:@"#" withString:@""];
        NSScanner *scanner = [NSScanner scannerWithString:colorString];
        [scanner scanHexInt:&rgbValue];
        return [[UIColor class] colorWithRed:((rgbValue & 0xFF0000) >> 16)/255.0f green:((rgbValue & 0xFF00) >> 8)/255.0f blue:(rgbValue & 0xFF)/255.0f alpha:1.0];
    }
    return defaultColor;
}

//date
ZHCSSFunc NSString * ZHDateString() {
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateStyle:NSDateFormatterMediumStyle];
    [dateFormatter setTimeStyle:NSDateFormatterShortStyle];
    [dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm:ss.SSS"];
    return [dateFormatter stringFromDate:[NSDate date]];
}

//UIViewContentMode
ZHCSSFunc UIViewContentMode ZHContentMode(NSString *value) {
    NSArray *arr = @[@"center", @"aspectFill"];
    switch ([arr indexOfObject:value]) {
        case 0:
            return UIViewContentModeCenter;
        default:
            return UIViewContentModeScaleAspectFill;
    }
}

ZHCSSFunc id ZHFormatReferenceData(NSString *value, NSDictionary *dataInfo) {
    if (![value isKindOfClass:[NSString class]]) return value;
    if (value.length == 0) return nil;
    
    NSString *flag1 = @"<?";
    NSString *flag2 = @"?>";
    BOOL isNeedFormat = ([value hasPrefix:flag1] && [value hasSuffix:flag2]);
    if (!isNeedFormat) return value;
    
    //解析 <?ads.0.xxxx.image?>
    NSString *newValue = [value substringWithRange:NSMakeRange(flag1.length, value.length - flag1.length - flag2.length)];
    if (newValue.length == 0) return nil;
    
    if (!dataInfo || dataInfo.allKeys.count == 0) return nil;
    
    NSArray *coms = [newValue componentsSeparatedByString:@"."];
    id result = dataInfo;
    for (NSString *com in coms) {
        if (com.length == 0) continue;
        //验证是否全是数字
        BOOL isNumber = ([com stringByTrimmingCharactersInSet:[NSCharacterSet decimalDigitCharacterSet]].length == 0);
        if (isNumber &&
            ([result isKindOfClass:[NSArray class]]) &&
            ([(NSArray *)result count] > com.integerValue)) {
            result = [(NSArray *)result objectAtIndex:com.integerValue];
            continue;
        }
        
        if (![result isKindOfClass:[NSDictionary class]]) return nil;
        
        //配合基金 转成小些
        NSString *lowCom = com.lowercaseString;
        
        NSArray *keys = [(NSDictionary *)result allKeys];
        if ([keys containsObject:com]) {
            result = [result valueForKey:com];
            if (result == [NSNull null]) result = nil;
            continue;
        }
        if ([keys containsObject:lowCom]){
            result = [result valueForKey:lowCom];
            if (result == [NSNull null]) result = nil;
            continue;
        }
        result = nil;
    }
    return result;
}

//运算
/**
 operation 无：直接取值  无需运算
 operation 有：noEmpty  拿值判断
              && eq || 拿left right值判断
 
 */
ZHCSSFunc id ZHInternalOperationData(NSDictionary *conditon, NSDictionary *dataInfo) {
    NSString *op = [conditon valueForKey:@"operation"];
    id returnValue = ZHFormatReferenceData([conditon valueForKey:@"return"], dataInfo);
    id value = ZHFormatReferenceData([conditon valueForKey:@"value"], dataInfo);
    if (value == [NSNull null]) value = nil;
    if ([value isKindOfClass:[NSString class]] && [(NSString *)value length] == 0)  value = nil;
    
    if (op.length == 0) {
        if (returnValue) return returnValue;
        if (value) return value;
        return nil;
    }
    
    if ([op isEqualToString:@"notEmpty"]) {
        return value ? (returnValue?:@(1)) : nil;
    }
    if ([op isEqualToString:@"empty"]) {
        return value ? nil : (returnValue?:@(1));
    }
    
    NSDictionary *leftCondition = [conditon valueForKey:@"left"];
    NSDictionary *rightCondition = [conditon valueForKey:@"right"];
    
    id leftResult = ZHInternalOperationData(leftCondition, dataInfo);
    id rightResult = ZHInternalOperationData(rightCondition, dataInfo);
    
    if ([op isEqualToString:@"and"]) {
        return (leftResult && rightResult) ? (returnValue?:@(1)) : nil;
    }
    if ([op isEqualToString:@"or"]) {
        return (leftResult || rightResult) ? (returnValue?:@(1)) : nil;
    }
    if ([op isEqualToString:@"eq"]) {
        if ([leftResult isKindOfClass:[NSNumber class]] &&
            [rightResult isKindOfClass:[NSNumber class]]) {
            return ([(NSNumber *)leftResult doubleValue] == [(NSNumber *)rightResult doubleValue]) ? (returnValue?:@(1)) : nil;
        }
        if ([leftResult isKindOfClass:[NSString class]] &&
            [rightResult isKindOfClass:[NSString class]]) {
            return ([leftResult isEqualToString:rightResult]) ? (returnValue?:@(1)) : nil;
        }
        return nil;
    }
    
    if (![leftResult isKindOfClass:[NSNumber class]] ||
        ![rightResult isKindOfClass:[NSNumber class]]) {
        return nil;
    }
    
    double leftNumber = [(NSNumber *)leftResult floatValue];
    double rightNumber = [(NSNumber *)rightResult floatValue];
    
    if ([op isEqualToString:@"ne"]) {
        return (leftNumber != rightNumber) ? (returnValue?:@(1)) : nil;
    }
    if ([op isEqualToString:@"gt"]) {
        return (leftNumber > rightNumber) ? (returnValue?:@(1)) : nil;
    }
    if ([op isEqualToString:@"ge"]) {
        return (leftNumber >= rightNumber) ? (returnValue?:@(1)) : nil;
    }
    if ([op isEqualToString:@"lt"]) {
        return (leftNumber < rightNumber) ? (returnValue?:@(1)) : nil;
    }
    if ([op isEqualToString:@"le"]) {
        return (leftNumber <= rightNumber) ? (returnValue?:@(1)) : nil;
    }
    return nil;
}
/** 数字运算 */
ZHCSSFunc NSNumber * ZHMathData(NSDictionary *conditon, NSDictionary *dataInfo) {
    NSString *math = [conditon valueForKey:@"math"];
    id leftResult = [conditon valueForKey:@"left"];
    id rightResult = [conditon valueForKey:@"right"];
    if (math.length == 0 || !leftResult || !rightResult) {
        return nil;
    }
    
    //获取数据
    leftResult = ZHFormatReferenceData(leftResult, dataInfo);
    rightResult = ZHFormatReferenceData(rightResult, dataInfo);
    
    //二级运算
    if ([leftResult isKindOfClass:[NSDictionary class]]) {
        leftResult = ZHMathData(leftResult, dataInfo);
    }
    //string转number
    if ([leftResult isKindOfClass:[NSString class]]) {
        BOOL isNumber = ([(NSString *)leftResult stringByTrimmingCharactersInSet:[NSCharacterSet decimalDigitCharacterSet]].length == 0);
        if (!isNumber) return nil;
        leftResult = @([(NSString *)leftResult doubleValue]);
    }
    //二级运算
    if ([rightResult isKindOfClass:[NSDictionary class]]) {
        rightResult = ZHMathData(rightResult, dataInfo);
    }
    //string转number
    if ([rightResult isKindOfClass:[NSString class]]) {
        BOOL isNumber = ([(NSString *)rightResult stringByTrimmingCharactersInSet:[NSCharacterSet decimalDigitCharacterSet]].length == 0);
        if (!isNumber) return nil;
        rightResult = @([(NSString *)rightResult doubleValue]);
    }
    
    //计算
    if ([leftResult isKindOfClass:[NSNumber class]] &&
        [rightResult isKindOfClass:[NSNumber class]]) {
        double leftNumber = [(NSNumber *)leftResult floatValue];
        double rightNumber = [(NSNumber *)rightResult floatValue];
        if ([math isEqualToString:@"+"]) {
            return @(leftNumber + rightNumber);
        }
        if ([math isEqualToString:@"-"]) {
            return @(leftNumber - rightNumber);
        }
        if ([math isEqualToString:@"*"]) {
            return @(leftNumber * rightNumber);
        }
        if ([math isEqualToString:@"/"]) {
            return @(leftNumber / rightNumber);
        }
    }
    return nil;
}
ZHCSSFunc id ZHOperationData(id value, NSDictionary *dataInfo) {
    if (!value) return nil;
    
    if ([value isKindOfClass:[NSNumber class]]) {
        return value;
    }
    //返回value
    if ([value isKindOfClass:[NSString class]]) {
        return ZHFormatReferenceData((NSString *)value, dataInfo);
    }
    //数字运算
    if ([value isKindOfClass:[NSDictionary class]]) {
        return ZHMathData(value, dataInfo);
    }
    //逻辑判断运算
    if ([value isKindOfClass:[NSArray class]]) {
        NSArray *valueArr = (NSArray *)value;
        if (valueArr.count < 2) return nil;
        
        for (NSDictionary *condition in valueArr) {
            if (![condition isKindOfClass:[NSDictionary class]]) return nil;
            
            id result = ZHInternalOperationData(condition, dataInfo);
            if (result) return result;
        }
    }
    return nil;
}



