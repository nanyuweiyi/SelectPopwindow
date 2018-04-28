
# MultiSelectPopWindow

### 用法
多选和单选用法几班一样

  1、在project的build.gradle：
  ```
  maven { 
    url 'https://jitpack.io' 
  }
  ```
  
  2、在app的build.gradle：	
 ```
 dependencies {
    implementation 'com.github.nanyuweiyi:SelectPopwindow:1.0.0'
 }
 ```
3、在Activity里调用：

多选调用： List<String> names = new ArrayList<>();
```
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
单选调用：List<String> names = new ArrayList<>();
```
    new SingleSelectPopWindow.Builder(MainActivity.this)
                        .setNameArray(names)
                        .setConfirmListener(new OnConfirmClickListenerSingle() {
                            @Override
                            public void onClick(int index, String selectedName) {
                                Toast.makeText(getApplication(), selectedName, Toast.LENGTH_SHORT).show();
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




