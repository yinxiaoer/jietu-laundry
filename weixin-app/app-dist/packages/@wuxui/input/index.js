Component({
    externalClasses: ['wux-class'],
    options: {
        multipleSlots: true,
        addGlobalClass: true,
    },
    properties: {
        label: {
            type: String,
            value: '',
        },
        extra: {
            type: String,
            value: '',
        },
        defaultValue: {
            type: String,
            value: '',
        },
        value: {
            type: String,
            value: '',
            observer(newVal) {
                if (this.data.controlled) {
                    this.updated(newVal)
                }
            },
        },
        controlled: {
            type: Boolean,
            value: false,
        },
        type: {
            type: String,
            value: 'text',
        },
        password: {
            type: Boolean,
            value: false,
        },
        placeholder: {
            type: String,
            value: '',
        },
        placeholderStyle: {
            type: String,
            value: '',
        },
        placeholderClass: {
            type: String,
            value: 'input-placeholder',
        },
        disabled: {
            type: Boolean,
            value: false,
        },
        maxlength: {
            type: Number,
            value: 140,
        },
        cursorSpacing: {
            type: Number,
            value: 11,
        },
        focus: {
            type: Boolean,
            value: false,
        },
        confirmType: {
            type: String,
            value: 'done',
        },
        confirmHold: {
            type: Boolean,
            value: false,
        },
        cursor: {
            type: Number,
            value: -1,
        },
        selectionStart: {
            type: Number,
            value: -1,
        },
        selectionEnd: {
            type: Number,
            value: -1,
        },
        adjustPosition: {
            type: Boolean,
            value: true,
        },
        clear: {
            type: Boolean,
            value: false,
        },
        error: {
            type: Boolean,
            value: false,
        },
    },
    data: {
        inputValue: '',
        inputFocus: false,
    },
    methods: {
        updated(inputValue) {
            if (this.data.inputValue !== inputValue) {
                this.setData({
                    inputValue,
                })
            }
        },
        onChange(e) {
            if (!this.data.controlled) {
                this.updated(e.detail.value)
            }

            this.triggerEvent('change', e.detail)
        },
        onFocus(e) {
            this.clearTimer()

            this.setData({
                inputFocus: true,
            })

            this.triggerEvent('focus', e.detail)
        },
        onBlur(e) {
            this.setTimer()

            this.triggerEvent('blur', e.detail)
        },
        onConfirm(e) {
            this.triggerEvent('confirm', e.detail)
        },
        onClear() {
            const { controlled, inputValue } = this.data

            this.setData({
                inputValue: controlled ? inputValue : '',
            })

            this.triggerEvent('clear', { value: '' })
        },
        onError() {
            this.triggerEvent('error', { value: this.data.inputValue })
        },
        setTimer() {
            this.clearTimer()

            this.timeout = setTimeout(() => {
                this.setData({
                    inputFocus: false,
                })
            }, 200)
        },
        clearTimer() {
            if (this.timeout) {
                clearTimeout(this.timeout)
                this.timeout = null
            }
        },
    },
    attached() {
        const { defaultValue, value, controlled } = this.data
        const inputValue = controlled ? value : defaultValue

        this.updated(inputValue)
    },
})
