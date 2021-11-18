Component({
    externalClasses: ['wux-class'],
    properties: {
        size: {
            type: String,
            value: 'default',
        },
        bodyStyle: {
            type: String,
            value: '',
        },
    },
    methods: {
        onTap() {
            this.triggerEvent('click')
        },
    },
})