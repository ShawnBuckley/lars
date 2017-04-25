const gulp = require('gulp');
const ts = require('gulp-typescript');
const sass = require('gulp-sass');
const sourcemaps = require('gulp-sourcemaps');

const baseDir = 'src/main/';

gulp.task('scss', function() {
  return gulp.src(baseDir + 'scss/**/*.scss')
    .pipe(sourcemaps.init())
    .pipe(sass().on('error', sass.logError))
    .pipe(sourcemaps.write('sourcemaps/'))
    .pipe(gulp.dest(baseDir + 'webapp/'))
});

gulp.task('typescript', function() {
  return gulp.src(baseDir + 'typescript/**/*.ts')
    .pipe(sourcemaps.init())
    .pipe(ts.createProject(baseDir + 'typescript/tsconfig.json')())
    .pipe(sourcemaps.write('sourcemaps/'))
    .pipe(gulp.dest(baseDir + 'webapp/'));
});

gulp.task('default', ['typescript', 'scss']);