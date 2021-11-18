import Vue from 'vue';

// v-dialogDrag: 弹窗拖拽
Vue.directive('dialogDrag', {
    bind(el, binding, vnode, oldVnode) {
        const dialogHeaderEl = el.querySelector('.el-dialog__header');
            dialogHeaderEl.style.cursor = 'move';
                let oDiv = el;   //当前元素
                    let self = this;  //上下文
                    oDiv.onmousedown = function (e) {
                    //鼠标按下，计算当前元素距离可视区的距离
                        let disX = e.clientX - oDiv.offsetLeft;
                        let disY = e.clientY - oDiv.offsetTop;

                        document.onmousemove = function (e) {
                        //通过事件委托，计算移动的距离 
                            let l = e.clientX - disX;
                            let t = e.clientY - disY;
                        //移动当前元素  
                            //边界限制
                            l = l<=0?0:l;
                            t = t<=0?0:t;
                            t = t>=300?300:t;
                            l = l>=300?300:l;
                            oDiv.style.left = l + 'px';
                            oDiv.style.top = t + 'px';
                            //将此时的位置传出去
                         
                            binding.value({x:l,y:t})
                        };
                        document.onmouseup = function (e) {
                        
                            document.onmousemove = null;
                            document.onmouseup = null;
                        };
                    };
    }
})

// v-dialogDragWidth: 弹窗宽度拖大 拖小
Vue.directive('dialogDragWidth', {
    bind(el, binding, vnode, oldVnode) {
        const dragDom = binding.value.$el.querySelector('.el-dialog');

        el.onmousedown = (e) => {
            
            // 鼠标按下，计算当前元素距离可视区的距离
            const disX = e.clientX - el.offsetLeft;
            
            document.onmousemove = function (e) {
                e.preventDefault(); // 移动时禁用默认事件

                // 通过事件委托，计算移动的距离 
                const l = e.clientX - disX;
                dragDom.style.width = `${l}px`;
            };

            document.onmouseup = function (e) {
                document.onmousemove = null;
                document.onmouseup = null;
            };
        }  
    }})