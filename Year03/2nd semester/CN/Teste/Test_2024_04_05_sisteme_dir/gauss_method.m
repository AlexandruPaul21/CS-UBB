function x=gauss_method(A,b)
%eliminare_gaussiana - eliminare gaussiana cu pivot scalat pe coloana
%apel x=eliminare_gaussiana(A,b)
%A - matricea, b - vectorul termenilor liberi
%x - solutia

    [~,n]=size(A);
    x=zeros(size(b));
    s=sum(abs(A),2);
    A=[A,b]; %matricea extinsa
    piv=1:n;
    %Eliminare
    for i=1:n-1
        [u,p]=max(abs(A(i:n,i))./s(i:n)); %pivotare
        p=p+i-1;
        if u==0, error('nu exista solutie unica'), end
        if p~=i %interschimbare linii
            piv([i,p])=piv([p,i]);
        end
        for j=i+1:n
            m=A(piv(j),i)/A(piv(i),i);
            A(piv(j),i+1:n+1)=A(piv(j),i+1:n+1)-m*A(piv(i),i+1:n+1);
        end
    end
    %substitutie inversa
    x(n)=A(piv(n),n+1)/A(piv(n),n);
    for i=n-1:-1:1
        x(i)=(A(piv(i),n+1)-A(piv(i),i+1:n)*x(i+1:n))/A(piv(i),i);
    end
end