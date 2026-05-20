import numpy as np
import pandas as pd
import matplotlib as plt
import plotly.express as px
import seaborn as sns


iris = sns.load_dataset('iris') #Load Data
#iris.drop('Id',inplace=True,axis=1) #Drop Id column
#iris.drop(columns=iris.columns[0],inplace=True)

iris.head().style.background_gradient(sns.color_palette("YlOrBr", as_cmap=True))

X = iris.iloc[:,:-1] #Set our training data

y = iris.iloc[:,-1] #Set training labels

# fig = px.pie(iris, 'species',color_discrete_sequence=['#ffffd4 ','#fe9929 ','#993404 '],title='Data Distribution',template='plotly_dark')

# fig.show()

# fig = px.box(data_frame=iris, x='species',y='sepal_length',color='species',color_discrete_sequence=['#ffffd4 ','#fe9929 ','#993404 '], template='plotly_dark',orientation='v')
 
# fig.show()

# fig = px.histogram(data_frame=iris, x='sepal_length',color='species',color_discrete_sequence=['#ffffd4 ','#fe9929 ','#993404 '], template='plotly_dark',nbins=50)

# fig.show()

class KNN:
    """
    K-Nearest Neighbors (KNN) classification algorithm

    Parameters:
    -----------
    n_neighbors : int, optional (default=5)
        Number of neighbors to use in the majority vote.

    Methods:
    --------
    fit(X_train, y_train):
        Stores the values of X_train and y_train.

    predict(X):
        Predicts the class labels for each example in X.

    """
    def __init__(self, n_neighbors=5):
        self.n_neighbors = n_neighbors
        
    def euclidean_distance(self, x1, x2):
        """
        Calculate the Euclidean distance between two data points.

        Parameters:
        -----------
        x1 : numpy.ndarray, shape (n_features,)
            A data point in the dataset.
        
        x2 : numpy.ndarray, shape (n_features,)
            A data point in the dataset.

        Returns:
        --------
        distance : float
            The Euclidean distance between x1 and x2.
        """
        return np.linalg.norm(x1 - x2)

    def fit(self, X_train, y_train):
        """
        Stores the values of X_train and y_train.

        Parameters:
        -----------
        X_train : numpy.ndarray, shape (n_samples, n_features)
            The training dataset.

        y_train : numpy.ndarray, shape (n_samples,)
            The target labels.
        """
        self.X_train = X_train
        self.y_train = y_train

    def predict(self, X):
        """
        Predicts the class labels for each example in X.

        Parameters:
        -----------
        X : numpy.ndarray, shape (n_samples, n_features)
            The test dataset.

        Returns:
        --------
        predictions : numpy.ndarray, shape (n_samples,)
            The predicted class labels for each example in X.
        """
        # Create empty array to store the predictions
        predictions = []
        # Loop over X examples
        for x in X:
            # Get prediction using the prediction helper function
            prediction = self._predict(x)
            # Append the prediction to the predictions list
            predictions.append(prediction)
        return np.array(predictions)

    def _predict(self, x):
        """
        Predicts the class label for a single example.

        Parameters:
        -----------
        x : numpy.ndarray, shape (n_features,)
            A data point in the test dataset.

        Returns:
        --------
        most_occuring_value : int
            The predicted class label for x.
        """
        # Create empty array to store distances
        distances = []
        # Loop over all training examples and compute the distance between x and all the training examples 
        for x_train in self.X_train:
            distance = self.euclidean_distance(x, x_train)
            distances.append(distance)
        distances = np.array(distances)
        
        # Sort by ascendingly distance and return indices of the given n neighbours
        n_neighbors_idxs = np.argsort(distances)[: self.n_neighbors]
        
        # Get labels of n-neighbour indexes
        labels = self.y_train[n_neighbors_idxs]                  
        labels = list(labels)
        # Get the most frequent class in the array
        most_occuring_value = max(labels, key=labels.count)
        return most_occuring_value
    
def train_test_split(X, y, random_state=42, test_size=0.2):
    """
    Splits the data into training and testing sets.

    Parameters:
        X (numpy.ndarray): Features array of shape (n_samples, n_features).
        y (numpy.ndarray): Target array of shape (n_samples,).
        random_state (int): Seed for the random number generator. Default is 42.
        test_size (float): Proportion of samples to include in the test set. Default is 0.2.

    Returns:
        Tuple[numpy.ndarray]: A tuple containing X_train, X_test, y_train, y_test.
    """
    # Get number of samples
    n_samples = X.shape[0]

    # Set the seed for the random number generator
    np.random.seed(random_state)

    # Shuffle the indices
    shuffled_indices = np.random.permutation(np.arange(n_samples))

    # Determine the size of the test set
    test_size = int(n_samples * test_size)

    # Split the indices into test and train
    test_indices = shuffled_indices[:test_size]
    train_indices = shuffled_indices[test_size:]

    # Split the features and target arrays into test and train
    X_train, X_test = X[train_indices], X[test_indices]
    y_train, y_test = y[train_indices], y[test_indices]

    return X_train, X_test, y_train, y_test

X_train, X_test, y_train, y_test = train_test_split(X.values, y.values, test_size = 0.2, random_state=42) #split the  data into traing and validating

model = KNN(7)
model.fit(X_train, y_train)

def compute_accuracy(y_true, y_pred):
    """
    Computes the accuracy of a classification model.

    Parameters:
    y_true (numpy array): A numpy array of true labels for each data point.
    y_pred (numpy array): A numpy array of predicted labels for each data point.

    Returns:
    float: The accuracy of the model, expressed as a percentage.
    """
    y_true = y_true.flatten()
    total_samples = len(y_true)
    correct_predictions = np.sum(y_true == y_pred)
    return (correct_predictions / total_samples) 

predictions = model.predict(X_test)
accuracy = compute_accuracy(y_test, predictions)
print(f" our model got accuracy score of : {accuracy}")  