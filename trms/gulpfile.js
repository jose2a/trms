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
        `${paths.nodeModules}admin-lte/dist/css/AdminLTE.min.css`,
        `${paths.nodeModules}admin-lte/dist/css/skins/_all-skins.min.css`,
        `${paths.nodeModules}bootstrap/dist/css/bootstrap.min.css`,
        `${paths.nodeModules}datatables.net-bs/css/dataTables.bootstrap.min.css`,
        `${paths.nodeModules}toastr/build/toastr.min.css`
    ];

    return gulp.src(cssToCopy)
        .pipe(gulp.dest(`${paths.stylesDest}`));
});

gulp.task('copy:js', () => {
    const javascriptToCopy = [
        `${paths.nodeModules}admin-lte/dist/js/adminlte.min.js`,
        `${paths.nodeModules}bootstrap/dist/js/bootstrap.min.js`,
        `${paths.nodeModules}jquery/dist/jquery.min.js`,
        `${paths.nodeModules}datatables.net/js/jquery.dataTables.min.js`,
        `${paths.nodeModules}datatables.net-bs/js/dataTables.bootstrap.min.js`,
        `${paths.nodeModules}toastr/build/toastr.min.js`
    ];

    return gulp.src(javascriptToCopy)
        .pipe(gulp.dest(`${paths.scriptsDest}`));
});

gulp.task('copy:img', done => {
	   gulp.src(`${paths.nodeModules}admin-lte/dist/img/**/*.{png,gif,jpg}`)
	   .pipe(gulp.dest(`${paths.imgsDest}`));
	   
	   done();
});

gulp.task('default', gulp.parallel('copy:css', 'copy:js', 'copy:img'))
