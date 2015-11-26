var gulp = require('gulp');
var ts = require('gulp-typescript');

gulp.task('default', function () {
    return gulp.src('src/main/typescript/*.ts')
        .pipe(ts({
            noImplicitAny: true,
            out: 'compiled.js'
        }))
        .pipe(gulp.dest('src/main/webapp/'));
});