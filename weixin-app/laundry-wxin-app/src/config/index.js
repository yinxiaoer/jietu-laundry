const environment = 'dev'
export const config = environment === 'dev' ? require('./dev') : require('./pro')

