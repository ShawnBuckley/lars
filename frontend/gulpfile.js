var gulp = require('gulp');
var pug = require('gulp-pug');

gulp.task('default', ['pug'], function() {});

gulp.task('pug', function() {
    gulp.src('./src/main/pug/*.jade ')
        .pipe(pug({
            locals: {}
        }))
        .pipe(gulp.dest('build/frontend'))
});
