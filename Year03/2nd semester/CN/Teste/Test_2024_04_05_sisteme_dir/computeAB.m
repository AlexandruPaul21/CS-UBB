function [A, B] = computeAB(n)

A = diag(3*ones(n,1)) + diag(-1*ones(n-1,1),1) + diag(-1*ones(n-1,1),-1);
    
for i = 1:n
    if (i == n / 2 || i == n / 2 + 1)
       continue
    end
    A(i,n+1-i) = 1/2;
end

B = [2.5; 1.5*ones((n-4)/2,1); 1.0; 1.0; 1.5*ones((n-4)/2,1); 2.5];

end