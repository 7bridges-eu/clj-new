(ns clj.new.lib
  "Generate a library project."
  (:require [clj.new.templates :refer [renderer year date project-name
                                       ->files sanitize-ns name-to-path
                                       multi-segment]]))

(defn lib
  "A general project template for libraries.

Accepts a group id in the project name: `clj -A:new lib foo.bar/baz`"
  [name]
  (let [render (renderer "lib")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)
              :date (date)}]
    (println "Generating a project called"
             (project-name name)
             "based on the 'lib' template.")
    (println "The lib template is intended for library projects, not applications.")
    (->files data
             ["deps.edn" (render "deps.edn" data)]
             ["README.md" (render "README.md" data)]
             ["doc/intro.md" (render "intro.md" data)]
             [".gitignore" (render "gitignore" data)]
             [".hgignore" (render "hgignore" data)]
             ["src/{{nested-dirs}}.clj" (render "core.clj" data)]
             ["test/{{nested-dirs}}_test.clj" (render "test.clj" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["CHANGELOG.md" (render "CHANGELOG.md" data)]
             "resources/.keep" "")))
