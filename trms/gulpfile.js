const gulp = require('gulp');
const concat = require('gulp-concat');

const paths = {
    nodeModules: './node_modules/',
    scriptsDest: './src/main/webapp/resources/js/',
    stylesDest: './src/main/webapp/resources/css/',
    imgsDest: './src/main/webapp/resources/img/'
};

gulp.task('copy:css', () => {
    const cssToCopy = [
        `${paths.nodeModules}admin-lte/dist/css/AdminLTE.min.css`
    ];

    return gulp.src(cssToCopy)   
        .pipe(concat('vendor.css'))
        .pipe(gulp.dest(`${paths.stylesDest}`));
});

gulp.task('copy:js', () => {
    const javascriptToCopy = [
        `${paths.nodeModules}admin-lte/dist/js/adminlte.min.js`
    ];

    return gulp.src(javascriptToCopy)
        .pipe(concat('vendor.js'))
        .pipe(gulp.dest(`${paths.scriptsDest}`));
});

gulp.task('copy:img', function() {
	   gulp.src(`${paths.nodeModules}admin-lte/dist/img/**/*.{png,gif,jpg}`)
	   .pipe(gulp.dest(`${paths.imgsDest}`));
});

gulp.task('default', gulp.parallel('copy:css', 'copy:js', 'copy:img'))
