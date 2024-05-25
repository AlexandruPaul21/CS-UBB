syms x
format longg

fprintf("a) tk=-1+k*(2/n)\n");
for i=10:15
    fprintf('i= %d\n',i);
    puncte_echidistante=linspace(-1,1,i);

    %vandermonde
    v=vander(puncte_echidistante);
    cond_vander = cond(v)

    %cebisev
    norma_chebisev=norm(v,'inf')

    raport=cond_vander/norma_chebisev
end


fprintf("b) tk=1/k\n");
for i=10:15
    fprintf('i= %d\n',i);
    puncte_echidistante=1:1:i;

    t_k=1./puncte_echidistante;

    %vandermonde
    v=vander(puncte_echidistante);
    cond_vander = cond(v)

    %cebisev
    norma_chebisev=norm(v,'inf')
    raport=cond_vander/norma_chebisev

end
