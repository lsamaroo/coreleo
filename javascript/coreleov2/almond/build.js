({
    baseUrl: "js",
    out: "../../../bin/coreleov2.js",
    name: "almond",
    optimize: "none",
    include: "main",
    wrap: {
        startFile: "frags/start.js",
        endFile: "frags/end.js"
    }
})
