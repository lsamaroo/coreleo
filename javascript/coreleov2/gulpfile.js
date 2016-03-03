var gulp = require('gulp'),
    eslint = require('gulp-eslint'),
	gulpSequence = require('gulp-sequence'),
    beautify = require('gulp-jsbeautifier');

var sourceDirectory = 'src';
var targetDirectory = '../../bin';
var targetOutputFileName = 'coreleov2.js';
var jsFiles = [ sourceDirectory + '/**/*.js' ];

gulp.task('lint', function() {
  return gulp.src(jsFiles)
	    .pipe(eslint())
        .pipe(eslint.format())
        .pipe(eslint.failAfterError());
});

gulp.task('beautify', function() {
	  gulp.src(jsFiles)
	    .pipe(beautify())
	    .pipe(gulp.dest(sourceDirectory))
});

gulp.task('build', function(cb) {
	gulpSequence('lint', 'beautify')(cb);
});

gulp.task('default', ['build'] );

