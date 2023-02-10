fileID = fopen('keywords_spam.txt','r');
spam = textscan(fileID,'%s');
fclose(fileID);

fileID = fopen('keywords_ham.txt','r');
ham = textscan(fileID,'%s');
fclose(fileID);

fileID = fopen('email3.txt','r');
email = textscan(fileID,'%s');
fclose(fileID);

%fprintf('%s',email);
%fprintf('.\n');

words = unique([spam{1} ; ham{1}]);
nr_words = numel(words);

nr_spam = numel(spam{1});
nr_ham = numel(ham{1});
nr_cuv = nr_spam + nr_ham;
p_spam = nr_spam / nr_cuv;
p_ham = nr_ham / nr_cuv;

for i = 1:nr_words
    if ismember(words{i},email{1})
        p_spam *= sum(strcmp(words{i},spam{1}))/nr_spam;
        p_ham  *= sum(strcmp(words{i},ham{1}))/nr_ham;
    else 
    
        p_spam *= 1 - sum(strcmp(words{i},spam{1}))/nr_spam;
        p_ham *= 1 - sum(strcmp(words{i},ham{1}))/nr_ham;
    
    end
end

p_spam
p_ham

%fprintf('Toate cuvintele:'); fprintf(' %s',vector{1}{:}); fprintf('.\n');
%fprintf('Al doilea cuvant: %s.\n',vector{1}{2});

%vector_unic=unique(vector{1});
%fprintf('Cuvintele fara repetitii:'); fprintf(' %s',vector_unic{:}); fprintf('.\n');

%fprintf('Numarul de cuvinte: %d.\n',numel(vector{1}));

%ismember('efg',vector_unic)

%strcmp('abc',vector{1})'