import arrayTreeFilter from '../helpers/arrayTreeFilter'

const WUX_CASCADER = 'wux-cascader'
const defaultFieldNames = {
    label: 'label',
    value: 'value',
    children: 'children',
}

Component({
    externalClasses: ['wux-class', 'wux-scroll-view-class'],
    properties: {
        defaultValue: {
            type: Array,
            value: [],
        },
        value: {
            type: Array,
            value: [],
            observer(newVal) {
                if (this.data.controlled) {
                    this.setData({ activeValue: newVal }, () => this.getCurrentOptions(newVal))
                }
            },
        },
        controlled: {
            type: Boolean,
            value: false,
        },
        title: {
            type: String,
            value: '',
        },
        options: {
            type: Array,
            value: [],
            observer: 'getCurrentOptions',
        },
        chooseTitle: {
            type: String,
            value: '请选择',
        },
        visible: {
            type: Boolean,
            value: false,
        },
        defaultFieldNames: {
            type: Object,
            value: defaultFieldNames,
        },
    },
    data: {
        activeOptions: [],
        activeIndex: 0,
        bodyStyle: '',
        activeValue: [],
        showOptions: [],
        fieldNames: {},
    },
    methods: {
        getActiveOptions(activeValue) {
            const { options } = this.data
            const value = this.getFieldName('value')
            const childrenKeyName = this.getFieldName('children')

            return arrayTreeFilter(options, (option, level) => option[value] === activeValue[level], { childrenKeyName })
        },
        getShowOptions(activeValue) {
            const { options } = this.data
            const children = this.getFieldName('children')
            const result = this.getActiveOptions(activeValue).map((activeOption) => activeOption[children]).filter((activeOption) => !!activeOption)

            return [options, ...result]
        },
        getMenus(activeValue = [], hasChildren) {
            const { options, chooseTitle } = this.data
            const activeOptions = this.getActiveOptions(activeValue)

            if (hasChildren && activeOptions.length < options.length) {
                const value = this.getFieldName('value')
                const label = this.getFieldName('label')

                activeOptions.push({
                    [value]: WUX_CASCADER,
                    [label]: chooseTitle
                })
            }

            return activeOptions
        },
        getNextActiveValue(value, optionIndex) {
            let { activeValue } = this.data
            activeValue = activeValue.slice(0, optionIndex + 1)
            activeValue[optionIndex] = value
            this.setData({
                activeValue: activeValue
            })
        },
        updated(currentOptions, optionIndex, condition, callback) {
            const value = this.getFieldName('value')
            const children = this.getFieldName('children')
            const hasChildren = currentOptions[children] && currentOptions[children].length > 0
            this.getNextActiveValue(currentOptions[value], optionIndex)
            const activeOptions = this.getMenus(this.data.activeValue, hasChildren)
            const activeIndex = activeOptions.length - 1
            const showOptions = this.getShowOptions(this.data.activeValue)
            const params = {
                activeOptions,
                activeIndex,
                showOptions,
            }
            // 判断 hasChildren 计算需要更新的数据
            if (hasChildren || (this.data.activeValue.length === showOptions.length && (optionIndex = Math.max(0, optionIndex - 1)))) {
                params.bodyStyle = `transform: translate(${-50 * optionIndex}%)`
                params.showOptions = showOptions
            }

            // 判断是否需要 setData 更新数据
            if(condition){
                this.setData(params)
            }
            // 回调函数
            if (typeof callback === 'function') {
                // TODO 当当前点击的项的children为null或undefined 的时候关闭弹窗，done为true
                callback.call(this, currentOptions, activeOptions, !currentOptions.children)
            }
        },
        /**
         * 更新级联数据
         * @param {Array} activeValue 当前选中值
         */
        getCurrentOptions() {
            const activeValue = this.data.activeValue
            const optionIndex = Math.max(0, activeValue.length - 1)
            const activeOptions = this.getActiveOptions(activeValue)
            const currentOptions = activeOptions[optionIndex]

            if (currentOptions) {
                this.updated(currentOptions, optionIndex, true)
            } else {
                const value = this.getFieldName('value')
                const label = this.getFieldName('label')

                activeOptions.push({
                    [value]: WUX_CASCADER,
                    [label]: this.data.chooseTitle
                })
                const showOptions = this.getShowOptions(activeValue)
                const activeIndex = activeOptions.length - 1
                const params = {
                    showOptions,
                    activeOptions,
                    activeIndex,
                    bodyStyle: '',
                }
                this.setData(params)
            }
        },
        /**
         * 点击菜单时的回调函数
         */
        onMenuClick(e) {
            const { menuIndex } = e.currentTarget.dataset
            const index = menuIndex > 1 ? menuIndex - 1 : 0
            const bodyStyle = `transform: translate(${-50 * index}%)`

            this.setData({
                bodyStyle,
                activeIndex: menuIndex,
            })
        },
        /**
         * 点击选项时的回调函数
         */
        onItemSelect(e) {

            const { item, optionIndex } = e.currentTarget.dataset

            // 判断是否禁用
            if (!item || item.disabled) return
            // updated
            this.updated(item, optionIndex, !this.data.controlled, this.onChange)
        },
        /**
         * 组件关闭时的回调函数
         */
        onPopupClose() {
            this.triggerEvent('close')
        },
        /**
         * 选择完成时的回调函数
         */
        onChange(currentOptions = {}, activeOptions = [], done = false) {
            const options = activeOptions.filter((n) => n[this.getFieldName('value')] !== WUX_CASCADER)
            const value = options.map((n) => n[this.getFieldName('value')])
            if (currentOptions.isLeaf === false && !done) {
                this.emitEvent({ value, options, done: false })
                this.triggerEvent('load', { value, options })
                return
            }

            // 正常加载
            this.emitEvent({ value, options, done })
        },
        emitEvent(params = {}) {
            this.triggerEvent('change', params)
            // 当选择完成时关闭组件
            if (params.done) {
                this.onPopupClose()
            }
        },
        getFieldName(name) {
            return this.data.fieldNames[name]
        },
    },
    attached() {
        const { defaultValue, value, controlled } = this.data
        const activeValue = controlled ? value : defaultValue
        const fieldNames = Object.assign({}, defaultFieldNames, this.data.defaultFieldNames)
        this.setData({ activeValue, fieldNames }, () => this.getCurrentOptions(activeValue))
    },
})
