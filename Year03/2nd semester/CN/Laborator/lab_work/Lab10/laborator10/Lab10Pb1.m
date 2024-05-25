int = integral(@sin, 0, pi)

trapez = trapez(@sin, 0, pi, 100)
simpson = Simpson(@sin, 0, pi, 100)
dreptunghi = dreptunghi(@sin, 0, pi, 100)

trapez2 = trapez(@sin, 0, pi, 1000)
simpson2 = Simpson(@sin, 0, pi, 1000)
dreptunghi2 = dreptunghi(@sin, 0, pi, 1000)
