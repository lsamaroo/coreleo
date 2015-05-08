var gulp = require('gulp'),
    jshint = require('gulp-jshint'),
    rename = require('gulp-rename'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat');


var sourceDirectory = 'src';
var targetDirectory = 'bin';
var targetOutputFileName = 'coreleo.js';
var jsFileNames = ['Logger.js', 'Namespace.js', 'Util.js', 'PollerUtil.js', 'DialogUtil.js', 
               'MenuUtil.js', 'MobileUtil.js', 'TabUtil.js', 'IdleUtil.js'];
var jsFiles = [];
for (i = 0; i < jsFileNames.length; i++) {
	jsFiles[i] = sourceDirectory + '/**/' + jsFileNames[i];
}


gulp.task('lint', function() {
  return gulp.src([sourceDirectory+'/**/*.js'])
    .pipe(jshint({"expr": "true"}))
    .pipe(jshint.reporter('default'));
});


gulp.task('minify', function(){
    return gulp.src(jsFiles)
        .pipe(concat(targetOutputFileName))
        .pipe(uglify())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest( targetDirectory ))
});


gulp.task('default', function() {
    gulp.start('lint', 'minify');
});

