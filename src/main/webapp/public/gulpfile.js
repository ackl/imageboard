var gulp = require('gulp');
var sass = require('gulp-sass');
var concat = require('gulp-concat');

gulp.task('sass', function () {
    gulp.src('./src/sass/*.scss')
        .pipe(sass())
        .pipe(concat('style.css'))
        .pipe(gulp.dest('./dist/css/'));
});

gulp.task('watch', function() {
  gulp.watch('./src/sass/*.scss', ['sass']);
});

gulp.task('copy', function(){
  gulp.src('node_modules/font-awesome/fonts/*')
    .pipe(gulp.dest('dist/fonts/'));
});

gulp.task('default', ['sass', 'copy']);

