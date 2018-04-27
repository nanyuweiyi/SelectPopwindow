
# MultiSelectPopWindow

### 用法
多选和单选用法几班一样
```xml
   new MultiSelectPopWindow.Builder(this)
                           .setNameArray(names)
                           .setConfirmListener(new MultiSelectPopWindow.OnConfirmClickListener() {
                               @Override
                               public void onClick(ArrayList<Integer> indexList, ArrayList<String> selectedList) {
                               //do something
                               }
                           })
                           .setCancel("取消")
                           .setConfirm("完成")
                           .setTitle("班级列表")
                           .build()
                           .show();
```


## 其它
`Star`是对我的最大支持. 谢谢




