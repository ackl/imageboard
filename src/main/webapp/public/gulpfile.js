var gulp = require('gulp');
var gutil = require('gulp-util');
var sass = require('gulp-sass');
var concat = require('gulp-concat');
var watchify = require('watchify');
var browserify = require('browserify');
var source = require('vinyl-source-stream');
var sourcemaps = require('gulp-sourcemaps');
var buffer = require('vinyl-buffer');

gulp.task('sass', function () {
    gulp.src('./src/sass/*.scss')
        .pipe(sass())
        .pipe(concat('style.css'))
        .pipe(gulp.dest('./dist/css/'));
});

gulp.task('watch', function() {
    gulp.watch('./src/sass/*.scss', ['sass']);
    bundle();
});

gulp.task('copy', function(){
    gulp.src('bower_components/fontawesome/fonts/*')
        .pipe(gulp.dest('dist/fonts/'));
    gulp.src('bower_components/jquery/dist/jquery.js')
        .pipe(gulp.dest('dist/js/'));
});

//gulp.task('browserify', function() {
    //return browserify('./src/js/app.js')
        //.bundle()
        //.pipe(source('bundle.js'))
        //.pipe(gulp.dest('./dist/js/'));
//});

var bundler = watchify(browserify('./src/js/app.js', watchify.args));
bundler.transform('decanify');
bundler.transform('debowerify');
//bundler.transform('brfs');

gulp.task('js', bundle); // so you can run `gulp js` to build the file
bundler.on('update', bundle); // on any dep update, runs the bundler
bundler.on('log', gutil.log); // output build logs to terminal

function bundle() {
  return bundler.bundle()
    // log errors if they happen
    .on('error', gutil.log.bind(gutil, 'Browserify Error'))
    .pipe(source('bundle.js'))
    // optional, remove if you dont want sourcemaps
      .pipe(buffer())
      .pipe(sourcemaps.init({loadMaps: true})) // loads map from browserify file
      .pipe(sourcemaps.write('./')) // writes .map file
    //
    .pipe(gulp.dest('./dist/js/'));
}

gulp.task('default', ['js', 'sass', 'copy']);

