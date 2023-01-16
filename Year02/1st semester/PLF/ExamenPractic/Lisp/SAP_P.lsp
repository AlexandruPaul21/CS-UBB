;ins (L : list, E : element, Poz : Intreg, P : Intreg)
;insereaza un element intr-o lista pe o pozitie data
;L = lista in care trebuie sa adaugam
;E = elementul pe care trebuie sa il adaugam
;Poz = Pozitia pe care trebuie sa il adaugam
;P = pozitia la fiecare pas in executie
(defun ins (L E Poz P)
    (cond 
        ((and (null L) (= Poz P)) (list E))
        ((null L) nil)
        ((= Poz P) (append (cons E (list (car L))) (ins (cdr L) E Poz (+ P 1))))
        (T (cons (car L) (ins (cdr L) E Poz (+ P 1))))
    )
)

;inserare1 (L : list, E : element, Poz : Intreg)
;insereaza un element pe toate pozitiile disponibile intr-o lista
;L = lista in care trebuie sa adaugam
;E = elementul pe care trebuie sa il adaugam
;Poz = Pozitia pe care trebuie sa il adaugam pe E la un anumit pas
(defun inserare1 (E L N Poz)
    (cond 
        ((< N Poz) nil)
        (T (append (list (ins L E Poz 1)) (inserare1 E L N (+ Poz 1))))
    )
)

;len (L : lista)
;determina lungimea unei liste
;L = lista a carei lungime vrem sa o aflam
(defun len (L)
    (cond 
        ((null L) 0)
        (T (+ 1 (len (cdr L))))
    )
)

;inserare (E : element, L : list)
;functia main
;E = elementul ce trebuie adaugat 
;L = lista in care trebuie adaugat
(defun inserare (E L)
    (cond
        ((null L) (list E))
        (T (inserare1 E L (+ (len L) 1) 1))
    )
)