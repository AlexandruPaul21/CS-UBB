function PA=simulare_i(N=5000)
PA=0;
urn=['r','r','r','r','r','b','b','b','g','g'];
for rep=1:N
    balls=randsample(urn,3);
    if balls(1)=='r'||balls(2)=='r'||balls(3)=='r'
        PA++;
    end
end
PA=PA/N;