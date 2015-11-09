var gulp = require('gulp'),
    jshint = require('gulp-jshint'),
    rename = require('gulp-rename'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat');


var sourceDirectory = 'src/main/javascript';
var targetDirectory = 'bin';
var targetOutputFileName = 'coreleo.js';
var jsFileNames = ['Namespace.js', 'Logger.js', 'Util.js', 'PollerUtil.js', 'DialogUtil.js', 
               'MenuUtil.js', 'MobileUtil.js', 'TabUtil.js', 'IdleUtil.js'];
var jsFiles = [];
// append the source directory to create full path
for (var i = 0; i < jsFileNames.length; i++) {
	jsFiles[i] = sourceDirectory + '/**/' + jsFileNames[i];
}


gulp.task('lint', function() {
  return gulp.src(jsFiles)
    .pipe(jshint({"expr": "true"}))
    .pipe(jshint.reporter('default'));
});


gulp.task('minify', function(){
    gulp.src(jsFiles)
        .pipe(concat(targetOutputFileName))
        .pipe(gulp.dest( targetDirectory ))
        .pipe(rename({suffix: '.min'}))
        .pipe(uglify())
        .pipe(gulp.dest(targetDirectory));
});


gulp.task('default', ['lint','minify'] );

