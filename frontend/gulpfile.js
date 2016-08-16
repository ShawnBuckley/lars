var gulp = require('gulp');
var ts = require('gulp-typescript');
var pug = require('gulp-pug');

gulp.task('default', ['pug', 'typescript'], function() {});

gulp.task('typescript', function () {
    return gulp.src('src/main/typescript/*.ts')
        .pipe(ts({
            noImplicitAny: true,
            out: 'compiled.js'
        }))
        .pipe(gulp.dest('src/main/resources/'));
});

gulp.task('pug', function() {
    gulp.src('./src/main/pug/*.pug')
        .pipe(pug({
            locals: {}
        }))
        .pipe(gulp.dest('src/main/resources/'))
});
