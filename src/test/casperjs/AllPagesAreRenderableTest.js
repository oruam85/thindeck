/*globals casper:false */
[
    '/',
    '/xsl/layout.xsl',
    '/css/style.css',
    '/robots.txt',
    '/acc',
    '/r/fake-repo',
    '/r/fake-repo/log?task=1'
].forEach(
    function (page) {
        casper.test.begin(
            page + ' page can be rendered',
            function (test) {
                casper.start(
                    casper.cli.get("home") + page,
                    function () {
                        test.assertHttpStatus(200, page);
                    }
                );
                casper.run(
                    function () {
                        test.done();
                    }
                );
            }
        );
    }
);
