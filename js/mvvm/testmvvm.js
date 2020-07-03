
//实现思路: 通过Object.defineProperty 篡改原有设置逻辑, 新旧值不一致时, 同步视图
//监听视图修改动作,从而同步model 
//以下伪码说明. 

var view = {
    html: `<p>${this.newVal}</p>`,
    _renderView: function (newVal) {
        this.newVal = newVal;
        console.log('触发视图更新: ', this.html);
    },

}

function ViewModel(model, view) {

    this.model = model;
    this.view = view;
    var me = this;
    Object.keys(model).forEach(function (key) {
        Object.defineProperty(model, key, {
            enumerable: true,
            configurable: false,
            set: function (newVal) {
                if (model[key] != newVal) {
                    model[key] = newVal;
                }
                me.syncView(newVal);
            }
        })
    })
}

ViewModel.prototype = {
    constructor: ViewModel,

    syncView: function (newVal) {
        this.view._renderView(newVal);
    },

    syncModel: function (event) {
        this.model = event.target.getValue();
    },

    _init: function(){
        var me = this;
        this.view.html.on('change', function(event){
            me.syncModel(event);
        })
    }
}
