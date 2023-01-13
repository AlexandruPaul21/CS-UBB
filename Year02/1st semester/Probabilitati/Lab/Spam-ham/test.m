fileID = fopen('text.txt','r');
vector=textscan(fileID,'%s');
fclose(fileID);

fprintf('Toate cuvintele:'); fprintf(' %s',vector{1}{:}); fprintf('.\n');
fprintf('Al doilea cuvant: %s.\n',vector{1}{2});

vector_unic=unique(vector{1});
fprintf('Cuvintele fara repetitii:'); fprintf(' %s',vector_unic{:}); fprintf('.\n');

fprintf('Numarul de cuvinte: %d.\n',numel(vector{1}));

ismember('efg',vector_unic)

strcmp('abc',vector{1})'