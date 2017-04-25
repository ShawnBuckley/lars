const gulp = require('gulp');
const ts = require('gulp-typescript');
const sourcemaps = require('gulp-sourcemaps');

const baseDir = 'src/main/';

gulp.task('typescript', function() {
  return gulp.src(baseDir + 'typescript/**/*.ts')
    .pipe(sourcemaps.init())
    .pipe(ts.createProject(baseDir + 'typescript/tsconfig.json')())
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest(baseDir + 'webapp/'));
});

gulp.task('default', ['typescript']);