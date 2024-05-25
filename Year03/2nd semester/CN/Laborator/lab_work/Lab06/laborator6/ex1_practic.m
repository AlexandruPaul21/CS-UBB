x = [1900,1910,1920,1930,1940,1950,1960,1970,1980,1990,2000,2010];
y = [75.995,91.972,105.711,123.203,131.669,150.697,179.323, 203.212, 226.505, 249.633, 281.422, 308.786];

t=[1975, 2018];
lagrange_aprox = Lagrange(x,y,t)
bary_aprox= barycentricInterpolation(x,y,t, barycentricweigths(x))

x = [1990, 2000, 2010];
y= [249.633, 281.422, 308.786];

t=2018;
lagrange_aprox_2018 = Lagrange(x,y,t)
