function r = BooleVillarceauSym(f, a, b, n)
h = (b - a) / (n * 4);
r = 7 * f(a);
x = a + h;
for i = 1 : n-1
    r = r + 32 * f(x);
    x = x + h;
    r = r + 12 * f(x);
    x = x + h;
    r = r + 32 * f(x);
    x = x + h;
    r = r + 14 * f(x);
    x = x + h;
end
r = r + 32 * f(x);
x = x + h;
r = r + 12 * f(x);
x = x + h;
r = r + 32 * f(x);
r = r + 7 * f(b);
r = r * h * 2/45;
end
