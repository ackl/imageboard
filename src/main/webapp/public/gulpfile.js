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
    gulp.src('bower_components/fontawesome/fonts/*')
        .pipe(gulp.dest('dist/fonts/'));
    gulp.src('bower_components/jquery/dist/jquery.js')
        .pipe(gulp.dest('dist/js/'));
});

gulp.task('default', ['sass', 'copy']);

