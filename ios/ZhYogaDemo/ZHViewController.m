//
//  ZHViewController.m
//  ZhYogaDemo
//
//  Created by Zh on 2019/10/23.
//  Copyright © 2019 Zh. All rights reserved.
//

#import "ZHViewController.h"
#import "ZHDemoController.h"

@interface ZHViewController ()<UITableViewDataSource, UITableViewDelegate>
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSMutableArray *items;

@end

@implementation ZHViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    [self.navigationController.navigationBar setTranslucent:NO];
    self.automaticallyAdjustsScrollViewInsets = NO;

    NSString *path = [[[NSBundle mainBundle] bundlePath] stringByAppendingPathComponent:@"Resource.bundle"];
    NSData *data = [NSData dataWithContentsOfFile:[path stringByAppendingPathComponent:@"demo/demo.json"]];
    self.items = [[NSJSONSerialization JSONObjectWithData:data options:0 error:nil] mutableCopy];
    [self.view addSubview:self.tableView];
}

- (void)viewDidLayoutSubviews{
    [super viewDidLayoutSubviews];
    self.tableView.frame = self.view.bounds;
}

- (void)viewDidAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    
//    [self tableView:self.tableView didSelectRowAtIndexPath:[NSIndexPath indexPathForRow:2 inSection:7]];
}

- (UITableView *)tableView {
    if (!_tableView) {
        _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        if (@available(iOS 11.0, *)) {
            if ([_tableView respondsToSelector:@selector(setContentInsetAdjustmentBehavior:)]) {
                _tableView.contentInsetAdjustmentBehavior = UIScrollViewContentInsetAdjustmentNever;
            }
        }
        _tableView.tableFooterView = [UIView new];
    }
    return _tableView;
}

#pragma mark - UITableViewDelegate

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return self.items.count;
}

//行数
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    NSDictionary *item = self.items[section];
    NSArray *subItems = item[@"items"];
    BOOL isOpen = [(NSNumber *)item[@"open"] boolValue];
    return isOpen ? subItems.count : 0;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 60;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section{
    UITableViewHeaderFooterView *headerView = [tableView dequeueReusableHeaderFooterViewWithIdentifier:@"headerView"];
    if (!headerView) {
        headerView = [[UITableViewHeaderFooterView alloc] initWithReuseIdentifier:@"headerView"];
        UITapGestureRecognizer *gesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(headerViewClick:)];
        [headerView addGestureRecognizer:gesture];
    }
    headerView.tag = section;
    headerView.textLabel.text = self.items[section][@"title"];
    headerView.textLabel.font = [UIFont boldSystemFontOfSize:17];
    headerView.textLabel.textColor = [UIColor blackColor];
    return headerView;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section{
    return 50;
}

//cell
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"xxx"];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"xxx"];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    NSDictionary *item = self.items[indexPath.section];
    NSArray *subItems = item[@"items"];
    cell.textLabel.text = subItems[indexPath.row];
    return cell;
}

//选中
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    NSString *path = [[[NSBundle mainBundle] bundlePath] stringByAppendingPathComponent:@"Resource.bundle"];
    
    NSDictionary *item = self.items[indexPath.section];
    NSArray *subItems = item[@"items"];
    NSString *title = item[@"title"];
    
    path = [[path stringByAppendingPathComponent:title] stringByAppendingPathComponent:[NSString stringWithFormat:@"%@.json",subItems[indexPath.row]]];
    
    NSData *data = [NSData dataWithContentsOfFile:path];
    if (!data) return;
    NSDictionary *info = [NSJSONSerialization JSONObjectWithData:data options:0 error:nil];
    if (!info) return;
    
    UIViewController *vc;
    vc = [[ZHDemoController alloc] initWithInfo:info dataInfo:nil];
    vc.title = title;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)headerViewClick:(UITapGestureRecognizer *)tapGesture{
    UIView *headerView = tapGesture.view;
    
    NSDictionary *item = self.items[headerView.tag];
    
    NSMutableDictionary *newItem = [item mutableCopy];
    NSNumber *originOpen = [newItem valueForKey:@"open"];
    [newItem setValue:@(originOpen ? !originOpen.boolValue : YES) forKey:@"open"];
    [self.items replaceObjectAtIndex:headerView.tag withObject:newItem];
    [self.tableView reloadSections:[NSIndexSet indexSetWithIndex:headerView.tag] withRowAnimation:UITableViewRowAnimationAutomatic];
}
@end
