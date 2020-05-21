var webpack = require('webpack')

module.exports = {
    entry: ['./app/main.js'],
    output: {
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                include: '/app/',
                loader: 'babel-loader',
                options: {
                    presets: [['es2015', { loose: true }]],
                    cacheDirectory: true,
                    plugins: ['transform-runtime']
                }
            }
        ]
    },
    plugins : [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        })
    ],
    mode: 'production'
};
