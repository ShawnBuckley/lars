var webpack = require("webpack");
var path = require("path");
module.exports = {
    entry: "./src/main/typescript/index.ts",
    output: {
        path: "build/frontend/",
        filename: "bundle.js"
    },
    resolve: {
        extensions: ['', '.webpack.js', '.web.js', '.ts', '.tsx', '.js']
    },
    module: {
        loaders: [
            { test: /\.css$/, loader: "style!css" },
            { test: /\.tsx?$/, loader: 'ts-loader' }
        ]
    },

    devServer: {
        inline: true,
        hot: false,
        contentBase: path.resolve(__dirname, 'build/frontend'),
        proxy: {
            '/rest/*': {
                target: 'http://localhost:8082/',
                secure: false
            }
        }
    }
};

