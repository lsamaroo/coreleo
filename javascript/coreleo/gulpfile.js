var gulp = require('gulp'),
    eslint = require('gulp-eslint'),
	gulpSequence = require('gulp-sequence'),
    beautify = require('gulp-jsbeautifier'),
    shell = require('gulp-shell');

var sourceDirectory = 'src';

var jsFiles = [ sourceDirectory + '/**/*.js' ];


gulp.task('lint', function() {
  return gulp.src(jsFiles)
	    .pipe(eslint({
	    	fix: true
	    }))
        .pipe(eslint.format())
        .pipe(eslint.failAfterError());
});


gulp.task('beautify', function() {
	  gulp.src(jsFiles)
	    .pipe(beautify())
	    .pipe(gulp.dest(sourceDirectory))
});

/**
 * I couldn't find a gulp tool for requirejs optimizer so instead 
 * i'm using gulp shell to execute the optimizer via nodejs.
 * 
 */
gulp.task('requirejs-optimizer', shell.task([
    'node node_modules/requirejs/bin/r.js -o build_scripts/build.js optimize=none'
]))


gulp.task('build', function(cb) {
	gulpSequence('lint', 'beautify', 'requirejs-optimizer')(cb);
});


gulp.task('default', ['build'] );