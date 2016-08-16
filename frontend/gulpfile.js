var gulp = require('gulp');
var ts = require('gulp-typescript');
var jade = require('gulp-jade');

gulp.task('default', ['jade', 'typescript'], function() {});

gulp.task('typescript', function () {
    return gulp.src('src/main/typescript/*.ts')
        .pipe(ts({
            noImplicitAny: true,
            out: 'compiled.js'
        }))
        .pipe(gulp.dest('src/main/resources/'));
});

gulp.task('jade', function() {
    gulp.src('./src/main/jade/*.jade')
        .pipe(jade({
            locals: {}
        }))
        .pipe(gulp.dest('src/main/resources/'))
});
