import numpy as np
import scipy.io as scio
offline_data = scio.loadmat('offline_data_random.mat')
online_data = scio.loadmat('online_data.mat')
offline_location, offline_rss = offline_data['offline_location'], offline_data['offline_rss']
trace, rss = online_data['trace'][0:1000, :], online_data['rss'][0:1000, :]

# Localzation accuracy
def accuracy(predictions, labels):
    return np.mean(np.sqrt(np.sum((predictions - labels)**2, 1)))

from sklearn import neighbors
knn_reg = neighbors.KNeighborsRegressor(40, weights='uniform', metric='euclidean')
%time knn_reg.fit(offline_rss, offline_location)
%time predictions = knn_reg.predict(rss)
acc = accuracy(predictions, trace)
print "accuracy: ", acc/100, "m"

# # Logistic For Classification
# labels = np.round(offline_location[:, 0]/100.0) * 100 + np.round(offline_location[:, 1]/100.0)
# from sklearn.linear_model import LogisticRegressionCV
# clf_l2_LR_cv = LogisticRegressionCV(Cs=20, penalty='l2', tol=0.001)
# predict_labels = clf_l2_LR.fit(offline_rss, labels).predict(rss)
# x = np.floor(predict_labels/100.0)
# y = predict_labels - x * 100
# predictions = np.column_stack((x, y)) * 100
# acc = accuracy(predictions, trace)
# print "accuracy: ", acc/100, 'm'

# # SVM For Classification
# from sklearn import svm
# labels = np.round(offline_location[:, 0]/100.0) * 100 + np.round(offline_location[:, 1]/100.0)
# clf_svc = svm.SVC(C=1000, tol=0.01, gamma=0.001)
# %time clf_svc.fit(offline_rss, labels)
# %time predict_labels = clf_svc.predict(rss)
# x = np.floor(predict_labels/100.0)
# y = predict_labels - x * 100
# predictions = np.column_stack((x, y)) * 100
# acc = accuracy(predictions, trace)
# print "accuracy: ", acc/100, 'm'

# knn Classification label and location transfer
labels = np.round(offline_location[:, 0]/100.0) * 100 + np.round(offline_location[:, 1]/100.0)
from sklearn import neighbors
knn_cls = neighbors.KNeighborsClassifier(n_neighbors=40, weights='uniform', metric='euclidean')
%time knn_cls.fit(offline_rss, labels)
%time predict_labels = knn_cls.predict(rss)
x = np.floor(predict_labels/100.0)
y = predict_labels - x * 100
predictions = np.column_stack((x, y)) * 100
acc = accuracy(predictions, trace)
print "accuracy: ", acc/100, 'm'

# Preprocessing data
from sklearn.preprocessing import StandardScaler
standard_scaler = StandardScaler().fit(offline_rss)
X_train = standard_scaler.transform(offline_rss)
Y_train = offline_location
X_test = standard_scaler.transform(rss)
Y_test = trace

# Cross Validation, choosing best k with max scores
from sklearn.model_selection import GridSearchCV
from sklearn import neighbors
parameters = {'n_neighbors':range(1, 50)}
knn_reg = neighbors.KNeighborsRegressor(weights='uniform', metric='euclidean')
clf = GridSearchCV(knn_reg, parameters)
clf.fit(offline_rss, offline_location)
scores = clf.cv_results_['mean_test_score']
k = np.argmax(scores) 

# plot the hyperparameter k with scores
import matplotlib.pyplot as plt
%matplotlib inline
plt.plot(range(1, scores.shape[0] + 1), scores, '-o', linewidth=2.0)
plt.xlabel("k")
plt.ylabel("score")
plt.grid(True)
plt.show()

# KNN regression
knn_reg = neighbors.KNeighborsRegressor(n_neighbors=k, weights='uniform', metric='euclidean')
predictions = knn_reg.fit(offline_rss, offline_location).predict(rss)
acc = accuracy(predictions, trace)
print "accuracy: ", acc/100, "m"

k = 29
data_num = range(100, 30000, 300)
acc = []
for i in data_num:
    knn_reg = neighbors.KNeighborsRegressor(n_neighbors=k, weights='uniform', metric='euclidean')
    predictions = knn_reg.fit(offline_rss[:i, :], offline_location[:i, :]).predict(rss)
    acc.append(accuracy(predictions, trace) / 100)

# draw the plot, training data and accuracy
import matplotlib.pyplot as plt
%matplotlib inline
plt.plot(data_num, acc, '-o', linewidth=2.0)
plt.xlabel("data number")
plt.ylabel("accuracy (m)")
plt.grid(True)
plt.show()